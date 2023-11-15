package me.ismartin.pokedexcompose.business.mappers

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import com.google.gson.JsonParser
import me.ismartin.pokedexcompose.business.formatPokemonId
import me.ismartin.pokedexcompose.business.models.PokedexPokemon
import me.ismartin.pokedexcompose.business.models.PokemonBackgroundColor
import me.ismartin.pokedexcompose.business.models.PokemonTypeColor
import me.ismartin.pokedexcompose.business.models.Sprites
import me.ismartin.pokedexcompose.data.local.entities.PokemonEntity
import me.ismartin.pokedexcompose.data.local.entities.PokemonSpritesEntity
import me.ismartin.pokedexcompose.data.local.entities.PokemonStatsEntity
import me.ismartin.pokedexcompose.data.local.entities.PokemonTypesEntity
import me.ismartin.pokedexcompose.data.remote.models.pokemon.Pokemon
import me.ismartin.pokedexcompose.data.remote.models.specie.Specie
import me.ismartin.pokedexcompose.ui.theme.Purple80
import timber.log.Timber
import java.lang.Exception

fun Pokemon.toEntity(specie: Specie?): PokemonEntity {
    val spritesEntity = PokemonSpritesEntity(
        frontDefault = sprites.frontDefault,
        backDefault = sprites.backDefault,
        frontShiny = sprites.frontShiny,
        dreamWorldFrontDefault = sprites.other.dreamWorld.frontDefault,
        homeFrontDefault = sprites.other.home.frontDefault,
        officialArtworkFrontDefault = sprites.other.officialArtwork.frontDefault
    )
    val statsEntity = stats.map { stat ->
        PokemonStatsEntity(
            baseStat = stat.baseStat,
            effort = stat.effort,
            statName = stat.stat.name,
            url = stat.stat.url
        )
    }
    val typesEntity = types.map {
        PokemonTypesEntity(
            slot = it.slot,
            typeName = it.type.name,
            url = it.type.url
        )
    }
    return PokemonEntity(
        id = id,
        name = name,
        sprites = spritesEntity,
        weight = weight,
        stats = statsEntity,
        types = typesEntity,
        baseHappiness = baseExperience,
        height = height,
        baseExperience = baseExperience,
        captureRate = specie?.captureRate,
        color = specie?.color.toString(),
        eggGroups = specie?.eggGroups.toString(),
        genderRate = specie?.genderRate,
        genera = specie?.genera.toString(),
        growthRate = specie?.growthRate.toString(),
        habitat = specie?.habitat.toString(),
        hasGenderDifferences = specie?.hasGenderDifferences,
        hatchCounter = specie?.hatchCounter,
        isBaby = specie?.isBaby,
        isMythical = specie?.isMythical,
        isLegendary = specie?.isLegendary
    )
}

fun PokemonEntity.toPokedexPokemon(): PokedexPokemon {
    val sprites = getSprites(sprites)
    val backgroundColor = getBackgroundColor(color, id)
    val types = getTypes(types, id)

    return PokedexPokemon(
        id = id.formatPokemonId(),
        name = name.capitalize(Locale.current),
        sprites = sprites,
        types = types,
        color = backgroundColor
    )
}

private fun getSprites(spriteEntity: PokemonSpritesEntity): Sprites {
    return Sprites(
        frontDefault = spriteEntity.frontDefault,
        backDefault = spriteEntity.backDefault,
        dreamWorldFrontDefault = spriteEntity.dreamWorldFrontDefault,
        homeFrontDefault = spriteEntity.homeFrontDefault,
        officialArtWorkFrontDefault = spriteEntity.officialArtworkFrontDefault,
    )
}

private fun getBackgroundColor(colorJson: String?, id: Int): Color {
    return try {
        PokemonBackgroundColor.getColor(JsonParser.parseString(colorJson).asJsonObject.get("name").asString)
    } catch (e: Exception) {
        Timber.e("Cannot get color for pokemon $id - ${e.message}")
        Purple80
    }
}

private fun getTypes(types: List<PokemonTypesEntity>, id: Int): List<Pair<String, Color>> {
    return types
        .sortedBy { it.slot }
        .map { type ->
            val color = PokemonTypeColor.getColor(type.typeName)
            val typeName = type.typeName.capitalize(Locale.current)
            Pair(typeName, color)
        }
}
