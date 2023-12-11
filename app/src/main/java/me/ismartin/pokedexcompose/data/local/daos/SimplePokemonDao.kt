package me.ismartin.pokedexcompose.data.local.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import me.ismartin.pokedexcompose.data.local.entities.SimplePokemonEntity

@Dao
interface SimplePokemonDao {

    @Query("SELECT * FROM simple_pokemons ORDER BY simple_pokemons.id")
    fun getAllSimplePokemon(): List<SimplePokemonEntity>

    @Query("SELECT COUNT(*) FROM simple_pokemons")
    fun getSimplePokemonCount(): Int

    @Upsert
    fun upsertSimplePokemons(pokemons: List<SimplePokemonEntity>)
}
