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
import me.ismartin.pokedexcompose.data.remote.models.pokemon.Pokemon
import me.ismartin.pokedexcompose.data.remote.models.specie.Specie
import me.ismartin.pokedexcompose.ui.theme.Purple80
import java.lang.Exception

fun Pokemon.toEntity(specie: Specie?): PokemonEntity {
    return PokemonEntity(
        id = id,
        name = name,
        sprites = sprites.toString(),
        weight = weight,
        stats = stats.toString(),
        types = types.toString(),
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
    val sprites = getSprites(sprites, id)
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

private fun getSprites(spriteString: String, id: Int): Sprites {
    return try {
        val spriteJsonObject = JsonParser.parseString(spriteString).asJsonObject
        val otherSprites = spriteJsonObject.getAsJsonObject("other")
        val dreamWorldSprites = otherSprites.getAsJsonObject("dream_world")
        val homeSprites = otherSprites.getAsJsonObject("home")
        val officialArtworkSprites = otherSprites.getAsJsonObject("official-artwork")
        Sprites(
            frontDefault = if (spriteJsonObject != null) {
                spriteJsonObject.get("front_default")?.asString.orEmpty()
            } else "",
            backDefault = if (spriteJsonObject != null) {
                spriteJsonObject.get("back_default")?.asString.orEmpty()
            } else "",
            dreamWorldFrontDefault = if (dreamWorldSprites != null) {
                dreamWorldSprites.get("front_default")?.asString.orEmpty()
            } else null,
            homeFrontDefault = if (homeSprites != null) {
                homeSprites.get("front_default")?.asString.orEmpty()
            } else null,
            officialArtWorkFrontDefault = if (officialArtworkSprites != null) {
                officialArtworkSprites.get("front_default")?.asString.orEmpty()
            } else null
        )
    } catch (e: Exception) {
        println("MRTN - cannot get sprites object for pokemon $id - ${e.message}")
        Sprites()
    }
}

private fun getBackgroundColor(colorJson: String?, id: Int): Color {
    return try {
        PokemonBackgroundColor.getColor(JsonParser.parseString(colorJson).asJsonObject.get("name").asString)
    } catch (e: Exception) {
        println("MRTN - cannot get color for pokemon $id - ${e.message}")
        Purple80
    }
}

private fun getTypes(typesJson: String, id: Int): List<Pair<String, Color>> {
    return try {
        JsonParser.parseString(typesJson)
            .asJsonArray
            .map {
                val type = it.asJsonObject
                    .getAsJsonObject("type")
                    .get("name")
                    .asString
                    .capitalize(Locale.current)
                val color = PokemonTypeColor.getColor(type)
                Pair(type, color)
            }
    } catch (e: Exception) {
        println("MRTN - cannot get types for pokemon $id - ${e.message}")
        emptyList()
    }
}
