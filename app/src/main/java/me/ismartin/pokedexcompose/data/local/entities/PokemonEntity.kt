package me.ismartin.pokedexcompose.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemons")
data class PokemonEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val sprites: String,
    val weight: Int,
    val stats: String,
    val types: String,
    val baseExperience: Int,
    val height: Int,
    val baseHappiness: Int?,
    val captureRate: Int?,
    val color: String?,
    val eggGroups: String?,
    val genderRate: Int?,
    val genera: String?,
    val growthRate: String?,
    val habitat: String?,
    val hasGenderDifferences: Boolean?,
    val hatchCounter: Int?,
    val isBaby: Boolean?,
    val isLegendary: Boolean?,
    val isMythical: Boolean?,
)
