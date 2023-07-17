package me.ismartin.pokedexcompose.data.remote

import me.ismartin.pokedexcompose.data.remote.models.pokemon.Pokemon
import me.ismartin.pokedexcompose.data.remote.models.pokemon.PokemonPageResult
import me.ismartin.pokedexcompose.data.remote.models.specie.Specie
import me.ismartin.pokedexcompose.data.remote.models.specie.SpeciePageResult
import me.ismartin.pokedexcompose.data.remote.models.type.Type
import me.ismartin.pokedexcompose.data.remote.models.type.TypePageResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApiService {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
    ): Response<PokemonPageResult>

    @GET("pokemon/{id}")
    suspend fun getPokemonById(
        @Path("id") id: Int,
    ): Response<Pokemon>

    @GET("type")
    suspend fun getTypeList(): Response<TypePageResult>

    @GET("type/{id}")
    suspend fun getTypeById(
        @Path("id") id: Int,
    ): Response<Type>

    @GET("pokemon-species")
    suspend fun getPokemonSpecieList(): Response<SpeciePageResult>

    @GET("pokemon-species/{id}")
    suspend fun getPokemonSpecieById(
        @Path("id") id: Int
    ): Response<Specie>

    companion object {
        const val BASE_URL = "https://pokeapi.co/api/v2/"
    }
}
