package me.ismartin.pokedexcompose.ui.splash

import me.ismartin.pokedexcompose.business.models.SimplePokemonModel

data class SplashState(
    val status: String = "Loading",
    val simplePokemon: List<SimplePokemonModel> = emptyList()
)
