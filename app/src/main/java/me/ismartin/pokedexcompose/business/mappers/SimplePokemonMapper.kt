package me.ismartin.pokedexcompose.business.mappers

import me.ismartin.pokedexcompose.business.models.SimplePokemonModel
import me.ismartin.pokedexcompose.data.local.entities.SimplePokemonEntity

fun SimplePokemonEntity.toModel() = SimplePokemonModel(
    id = id,
    name = name,
    url = url
)
