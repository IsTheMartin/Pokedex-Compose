package me.ismartin.pokedexcompose.data.remote

import me.ismartin.pokedexcompose.business.ApiResource
import me.ismartin.pokedexcompose.data.remote.models.pokemon.Pokemon
import me.ismartin.pokedexcompose.data.remote.models.pokemon.PokemonPaged
import me.ismartin.pokedexcompose.data.remote.models.specie.Specie
import me.ismartin.pokedexcompose.data.remote.models.specie.SpeciePageResult
import me.ismartin.pokedexcompose.data.remote.models.type.Type
import me.ismartin.pokedexcompose.data.remote.models.type.TypePageResult
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

interface RemoteRepository {

    suspend fun getTypes(): ApiResource<TypePageResult>
    suspend fun getTypeById(id: Int): ApiResource<Type>
    suspend fun getPokemons(offset: Int, limit: Int): ApiResource<PokemonPaged>
    suspend fun getPokemonList(): ApiResource<PokemonPaged>
    suspend fun getPokemonById(id: Int): ApiResource<Pokemon>
    suspend fun getSpecies(): ApiResource<SpeciePageResult>
    suspend fun getSpecieById(id: Int): ApiResource<Specie>
}

class RemoteRepositoryImpl @Inject constructor(
    private val api: PokeApiService
) : RemoteRepository {

    override suspend fun getTypes(): ApiResource<TypePageResult> {
        val apiResponse = api.getTypeList()
        return getApiResourceResponse(apiResponse)
    }

    override suspend fun getTypeById(id: Int): ApiResource<Type> {
        val apiResponse = api.getTypeById(id)
        return getApiResourceResponse(apiResponse)
    }

    override suspend fun getPokemons(offset: Int, limit: Int): ApiResource<PokemonPaged> {
        val apiResponse = api.getPokemonList(offset, limit)
        return getApiResourceResponse(apiResponse)
    }

    override suspend fun getPokemonList(): ApiResource<PokemonPaged> {
        val pokemonCountResponse = getApiResourceResponse(api.getPokemonList(offset = 0, limit = 1))
        var pokemonCount = 0
        when (pokemonCountResponse) {
            is ApiResource.Failure -> return pokemonCountResponse
            is ApiResource.Success -> pokemonCount = pokemonCountResponse.data?.count ?: 0
        }
        if (pokemonCount == 0) return ApiResource.Failure(message = "No pokemon found from api")
        return getApiResourceResponse(api.getPokemonList(offset = 0, limit = pokemonCount))
    }

    override suspend fun getPokemonById(id: Int): ApiResource<Pokemon> {
        val apiResponse = api.getPokemonById(id)
        return getApiResourceResponse(apiResponse)
    }

    override suspend fun getSpecies(): ApiResource<SpeciePageResult> {
        val apiResponse = api.getPokemonSpecieList()
        return getApiResourceResponse(apiResponse)
    }

    override suspend fun getSpecieById(id: Int): ApiResource<Specie> {
        val apiResponse = api.getPokemonSpecieById(id)
        return getApiResourceResponse(apiResponse)
    }

    private fun <T>getApiResourceResponse(apiResponse: Response<T>): ApiResource<T> {
        return when {
            !apiResponse.isSuccessful ||
                apiResponse.code() != 200 ||
                apiResponse.body() == null -> {
                Timber.w("Error on api service: ${apiResponse.headers()}\n${apiResponse.code()}\n${apiResponse.errorBody()?.string()}")
                ApiResource.Failure(
                    message = getErrorMessage(
                        errorCode = apiResponse.code(),
                        errorMessage = apiResponse.errorBody()?.string().orEmpty()
                    )
                )
            }
            else -> ApiResource.Success(data = apiResponse.body())
        }
    }

    private fun getErrorMessage(errorCode: Int, errorMessage: String): String {
        return "code: $errorCode \nmessage: $errorMessage"
    }
}
