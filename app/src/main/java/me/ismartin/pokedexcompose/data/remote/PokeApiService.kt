package me.ismartin.pokedexcompose.data.remote

import me.ismartin.pokedexcompose.data.remote.models.pokemon.PageResult
import me.ismartin.pokedexcompose.data.remote.models.pokemon.PokemonResult
import me.ismartin.pokedexcompose.data.remote.models.specie.Specie
import me.ismartin.pokedexcompose.data.remote.models.stat.Stat
import me.ismartin.pokedexcompose.data.remote.models.stat.StatPageResult
import me.ismartin.pokedexcompose.data.remote.models.type.Type
import me.ismartin.pokedexcompose.data.remote.models.type.TypePageResult
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
        @Path("id") id: Int,
    ): Call<PokemonResult>

    @GET("/type")
    fun getTypeList(): Call<TypePageResult>

    @GET("/type/{id}")
    fun getTypeById(
        @Path("id") id: Int,
    ): Call<Type>

    @GET("/stat")
    fun getStatList(): Call<StatPageResult>

    @GET("/stat/{id}")
    fun getStatById(
        @Path("id") id: Int,
    ): Call<Stat>

    @GET("/pokemon-species/{id}")
    fun getPokemonSpecieById(
        @Path("id") id: Int
    ): Call<Specie>
}
