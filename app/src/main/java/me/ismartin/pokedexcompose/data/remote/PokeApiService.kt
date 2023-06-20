package me.ismartin.pokedexcompose.data.remote

import me.ismartin.pokedexcompose.data.remote.models.pokemon.PageResult
import me.ismartin.pokedexcompose.data.remote.models.pokemon.PokemonResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApiService {

    @GET("/pokemon")
    fun getPokemonList(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
    ): Call<PageResult>

    @GET("/pokemon/{id}")
    fun getPokemonById(
        @Path("id") id: Int
    ): Call<PokemonResult>
}
