package me.ismartin.pokedexcompose.business.mappers

import me.ismartin.pokedexcompose.data.local.entities.PokemonEntity
import me.ismartin.pokedexcompose.data.remote.models.pokemon.Pokemon
import me.ismartin.pokedexcompose.data.remote.models.specie.Specie

fun Pokemon.toEntity(specie: Specie?): PokemonEntity {
    return PokemonEntity(
        id = this.id,
        name = this.name,
        sprites = this.sprites.toString(),
        weight = this.weight,
        stats = this.stats.toString(),
        types = this.types.toString(),
        baseHappiness = this.baseExperience,
        height = this.height,
        baseExperience = this.baseExperience,
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
