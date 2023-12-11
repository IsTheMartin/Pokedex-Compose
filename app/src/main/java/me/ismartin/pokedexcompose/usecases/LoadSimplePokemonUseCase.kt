package me.ismartin.pokedexcompose.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import me.ismartin.pokedexcompose.business.ApiResource
import me.ismartin.pokedexcompose.business.mappers.toModel
import me.ismartin.pokedexcompose.business.models.SimplePokemonModel
import me.ismartin.pokedexcompose.data.local.entities.SimplePokemonEntity
import me.ismartin.pokedexcompose.data.local.repositories.SimplePokemonRepository
import me.ismartin.pokedexcompose.data.remote.RemoteRepository
import me.ismartin.pokedexcompose.data.remote.models.pokemon.SimplePokemon
import me.ismartin.pokedexcompose.utils.getId
import timber.log.Timber
import javax.inject.Inject

class LoadSimplePokemonUseCase @Inject constructor(
    private val simplePokemonRepository: SimplePokemonRepository,
    private val remoteRepository: RemoteRepository,
) {
    // todo: rename everything
    suspend operator fun invoke(): Flow<List<SimplePokemonModel>> {
        val pokemonCount = simplePokemonRepository.getSimplePokemonCount()
        if (pokemonCount == 0) {
            val simplePokemonList = getSimplePokemonFromRemote()
            saveSimplePokemonToLocal(simplePokemonList)
        }

        return flowOf(simplePokemonRepository.getSimplePokemonList().map { it.toModel() })
    }

    private suspend fun getSimplePokemonFromRemote(): List<SimplePokemon> {
        return when (val pokemonListApi = remoteRepository.getPokemonList()) {
            is ApiResource.Failure -> emptyList()
            is ApiResource.Success -> pokemonListApi.data?.results ?: emptyList()
        }
    }

    private suspend fun saveSimplePokemonToLocal(simplePokemonList: List<SimplePokemon>) {
        val simplePokemonEntities = simplePokemonList.map {
            SimplePokemonEntity(
                id = it.url.getId(),
                name = it.name,
                url = it.url
            )
        }
        Timber.d("Saving ${simplePokemonEntities.size} pokemon into database")
        simplePokemonRepository.insertSimplePokemon(simplePokemonEntities)
    }
}
