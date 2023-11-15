package me.ismartin.pokedexcompose.data.local.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemons")
data class PokemonEntity(
    @PrimaryKey val id: Int,
    val name: String,
    @Embedded(prefix = "sprite_")
    val sprites: PokemonSpritesEntity,
    val weight: Int,
    val stats: List<PokemonStatsEntity>,
    val types: List<PokemonTypesEntity>,
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

data class PokemonTypesEntity(
    val slot: Int,
    val typeName: String,
    val url: String,
)

data class PokemonSpritesEntity(
    val frontDefault: String,
    val backDefault: String? = null,
    val frontShiny: String?,
    val dreamWorldFrontDefault: String?,
    val homeFrontDefault: String?,
    val officialArtworkFrontDefault: String?,
)

data class PokemonStatsEntity(
    val baseStat: Int,
    val effort: Int,
    val statName: String,
    val url: String,
)
