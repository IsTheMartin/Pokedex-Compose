package me.ismartin.pokedexcompose.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "simple_pokemons")
data class SimplePokemonEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val url: String
)
