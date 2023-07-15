package me.ismartin.pokedexcompose.data.remote

import me.ismartin.pokedexcompose.business.ApiResource
import me.ismartin.pokedexcompose.data.remote.models.pokemon.PokemonPageResult
import me.ismartin.pokedexcompose.data.remote.models.pokemon.PokemonResult
import me.ismartin.pokedexcompose.data.remote.models.specie.Specie
import me.ismartin.pokedexcompose.data.remote.models.specie.SpeciePageResult
import me.ismartin.pokedexcompose.data.remote.models.stat.Stat
import me.ismartin.pokedexcompose.data.remote.models.stat.StatPageResult
import me.ismartin.pokedexcompose.data.remote.models.type.Type
import me.ismartin.pokedexcompose.data.remote.models.type.TypePageResult
import retrofit2.Response
import javax.inject.Inject

interface RemoteRepository {

    suspend fun getStats(): ApiResource<StatPageResult>
    suspend fun getStatById(id: Int): ApiResource<Stat>
    suspend fun getTypes(): ApiResource<TypePageResult>
    suspend fun getTypeById(id: Int): ApiResource<Type>
    suspend fun getPokemons(offset: Int, limit: Int): ApiResource<PokemonPageResult>
    suspend fun getPokemonById(id: Int): ApiResource<PokemonResult>
    suspend fun getSpecies(): ApiResource<SpeciePageResult>
    suspend fun getSpecieById(id: Int): ApiResource<Specie>
}

class RemoteRepositoryImpl @Inject constructor(
    private val api: PokeApiService
) : RemoteRepository {

    override suspend fun getStats(): ApiResource<StatPageResult> {
        val apiResponse = api.getStatList()
        return getApiResourceResponse(apiResponse)
    }

    override suspend fun getStatById(id: Int): ApiResource<Stat> {
        val apiResponse = api.getStatById(id)
        return getApiResourceResponse(apiResponse)
    }

    override suspend fun getTypes(): ApiResource<TypePageResult> {
        val apiResponse = api.getTypeList()
        return getApiResourceResponse(apiResponse)
    }

    override suspend fun getTypeById(id: Int): ApiResource<Type> {
        val apiResponse = api.getTypeById(id)
        return getApiResourceResponse(apiResponse)
    }

    override suspend fun getPokemons(offset: Int, limit: Int): ApiResource<PokemonPageResult> {
        val apiResponse = api.getPokemonList(offset, limit)
        return getApiResourceResponse(apiResponse)
    }

    override suspend fun getPokemonById(id: Int): ApiResource<PokemonResult> {
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
