package me.ismartin.pokedexcompose.business

import android.net.Uri
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.ismartin.pokedexcompose.business.mappers.toEntity
import me.ismartin.pokedexcompose.data.local.LocalRepository
import me.ismartin.pokedexcompose.data.local.PokedexDatabase
import me.ismartin.pokedexcompose.data.local.entities.PokemonEntity
import me.ismartin.pokedexcompose.data.local.entities.RemoteKeyEntity
import me.ismartin.pokedexcompose.data.remote.RemoteRepository
import me.ismartin.pokedexcompose.data.remote.models.pokemon.SimplePokemon
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import kotlin.system.measureTimeMillis

@OptIn(ExperimentalPagingApi::class)
class PokedexMediator @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository,
    private val database: PokedexDatabase,
) : RemoteMediator<Int, PokemonEntity>() {

    override suspend fun initialize(): InitializeAction {
        val remoteKey = localRepository.getRemoteKeyById(REMOTE_KEY)
        return if (remoteKey == null) {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        } else {
            InitializeAction.SKIP_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PokemonEntity>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> localRepository.getRemoteKeyById(REMOTE_KEY)?.nextOffset ?: 0
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKey = localRepository.getRemoteKeyById(REMOTE_KEY)
                    if (remoteKey == null || remoteKey.nextOffset == 0) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }
                    remoteKey.nextOffset
                }
            }
            val pokemons = remoteRepository.getPokemons(
                offset = loadKey,
                limit = state.config.pageSize
            )
            val nextOffset = pokemons.data?.next.getNextOffset()
            val pokemonEntities = getPokemonEntity(pokemons.data?.results ?: emptyList())
            database.withTransaction {
                localRepository.insertPokemons(pokemonEntities)
                database.remoteKeyDao().insert(RemoteKeyEntity(id = "pokemon", nextOffset = nextOffset))
            }

            MediatorResult.Success(
                endOfPaginationReached = pokemons.data?.next == null
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getPokemonEntity(pokemons: List<SimplePokemon>): List<PokemonEntity> {
        println("MRTN > PokedexMediator.getPokemonEntity")
        var newPokemons = emptyList<PokemonEntity>()
        val measuredTime = measureTimeMillis {
            newPokemons = pokemons.mapNotNull { pokemon ->
                val pokemonId = pokemon.url.getId()
                withContext(Dispatchers.IO) {
                    println("MRTN > PokedexMediator.getPokemonEntity > calling getPokemonById = $pokemonId")
                    /*val a = async { remoteRepository.getPokemonById(pokemonId) }
                    a.await().data?.let { pokemonDetails ->
                        val specieId = pokemonDetails.species.url.getId()
                        val specie = remoteRepository.getSpecieById(specieId)
                        pokemonDetails.toEntity(specie.data)
                    }*/
                    remoteRepository.getPokemonById(pokemonId).data?.let { pokemonDetails ->
                        val specieId = pokemonDetails.species.url.getId()
                        val specie = remoteRepository.getSpecieById(specieId)
                        pokemonDetails.toEntity(specie.data)
                    }
                }
            }
        }
        println("MRTN > PokedexMediator.getPokemonEntity > operation took $measuredTime ms")
        return newPokemons
    }

    private fun String.getId(): Int {
        val splitUrl = this.split(URL_SEPARATOR)
        return splitUrl[splitUrl.size - 2].toInt()
    }

    private fun String?.getNextOffset(): Int {
        return if (this != null) {
            val uri = Uri.parse(this)
            uri.getQueryParameter(OFFSET_PARAMETER)?.toInt() ?: 0
        } else 0
    }

    companion object {
        private const val REMOTE_KEY = "pokemon"
        private const val OFFSET_PARAMETER = "offset"
        private const val URL_SEPARATOR = "/"
    }
}
