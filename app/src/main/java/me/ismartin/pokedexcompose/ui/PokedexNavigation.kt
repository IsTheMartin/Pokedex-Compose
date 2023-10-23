package me.ismartin.pokedexcompose.ui

sealed class PokedexNavigation(val route: String) {
    object PokedexScreen: PokedexNavigation("Pokedex")
}
