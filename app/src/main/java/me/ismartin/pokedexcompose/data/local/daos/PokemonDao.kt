package me.ismartin.pokedexcompose.data.local.daos

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import me.ismartin.pokedexcompose.data.local.entities.PokemonEntity

@Dao
interface PokemonDao {

    @Query("SELECT * FROM pokemons")
    fun getAllPokemon(): List<PokemonEntity>

    @Query("SELECT * FROM pokemons WHERE id=:id")
    fun getPokemonById(id: Int): PokemonEntity

    @Query("SELECT COUNT(*) FROM pokemons")
    fun getPokemonCount(): Int

    @Query("SELECT * FROM pokemons")
    fun getPagedPokedex(): PagingSource<Int, PokemonEntity>

    @Upsert
    fun upsertPokemons(pokemons: List<PokemonEntity>)

    @Query("DELETE FROM pokemons")
    fun deletePokemons()
}
