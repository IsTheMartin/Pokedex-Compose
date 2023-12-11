package me.ismartin.pokedexcompose.ui.navigation

sealed class AppScreens(val route: String) {
    object PokemonListScreen : AppScreens("PokemonList")
    object DetailsScreen : AppScreens("PokemonDetails")
}
