package me.ismartin.pokedexcompose.business

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import me.ismartin.pokedexcompose.data.remote.RemoteRepository
import me.ismartin.pokedexcompose.data.remote.models.pokemon.Pokemon
import me.ismartin.pokedexcompose.data.remote.models.pokemon.PokemonResult
import me.ismartin.pokedexcompose.data.remote.models.specie.Specie
import me.ismartin.pokedexcompose.data.remote.models.type.TypeResult
import javax.inject.Inject

class PokedexMediator @Inject constructor(
    private val remoteRepository: RemoteRepository
) {

    /**
     * 1. Download and save in DB: stats and types only first time
     * 2. Download pokemon list
     * 3. For each pokemon, download specie
     * 4. Map pokemon to entity
     * 5. Save in DB
     */
    suspend fun downloadAndSave() {
        downloadTypes()
        downloadPokemonsList()
    }

    private suspend fun downloadTypes() {
        var nextPage: Any? = null
        do {
            when (val types = remoteRepository.getTypes()) {
                is ApiResource.Failure -> Log.e(TAG, "error download types: ${types.message}")
                is ApiResource.Success -> {
                    nextPage = types.data?.next
                    saveTypes(types.data?.typeResults ?: emptyList())
                }
            }
        } while (nextPage != null)
    }

    private fun saveTypes(types: List<TypeResult>) {
        println("MRTN - Saving types = $types")
        // todo: save results into DB
    }

    private suspend fun downloadPokemonsList() {
        var offset = 0
        var nextPage: String? = null
        val speciesCount = downloadSpeciesCount()
        do {
            when (val pokemons = remoteRepository.getPokemons(offset, PAGE_SIZE)) {
                is ApiResource.Failure -> Log.e(TAG, "downloadPokemonsList: ${pokemons.message}")
                is ApiResource.Success -> {
                    offset += PAGE_SIZE
                    nextPage = pokemons.data?.next
                    savePokemons(
                        pokemonList = pokemons.data?.results ?: emptyList(),
                        speciesCount = speciesCount
                    )
                }
            }
        } while (nextPage != null)
    }

    private suspend fun savePokemons(pokemonList: List<PokemonResult>, speciesCount: Int) {
        pokemonList.forEach { pokemon ->
            val pokemonId = pokemon.url.getId()
            val pokemonDetails = downloadPokemonDetails(pokemonId)
            val pokemonSpecie = if (pokemonId <= speciesCount) {
                downloadSpecie(pokemonId)
            } else {
                flowOf(null)
            }
            combine(pokemonDetails, pokemonSpecie) { details, specie ->
                // todo: create entity
                specie?.name // for testing
            }.collect {
                // todo: save in db
                println("MRTN - Pokemon $pokemonId color = $it")
            }
        }
    }

    private suspend fun downloadSpeciesCount(): Int {
        return when (val species = remoteRepository.getSpecies()) {
            is ApiResource.Failure -> -1
            is ApiResource.Success -> species.data?.count ?: -1
        }
    }

    private suspend fun downloadPokemonDetails(id: Int): Flow<Pokemon?> {
        return when (val pokemon = remoteRepository.getPokemonById(id)) {
            is ApiResource.Failure -> {
                Log.e(TAG, "error download pokemon #$id : ${pokemon.message}")
                flowOf(null)
            }

            is ApiResource.Success -> {
                flowOf(pokemon.data)
            }
        }
    }

    private suspend fun downloadSpecie(id: Int): Flow<Specie?> {
        return when (val specie = remoteRepository.getSpecieById(id)) {
            is ApiResource.Failure -> {
                Log.e(TAG, "error download specie #$id: ${specie.message}")
                flowOf(null)
            }
            is ApiResource.Success -> { flowOf(specie.data) }
        }
    }

    companion object {
        private const val TAG = "PokedexMediator"
        private const val PAGE_SIZE = 20
    }

    private fun String.getId(): Int {
        val splitUrl = this.split("/")
        return splitUrl[splitUrl.size - 2].toInt()
    }
}
