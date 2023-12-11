package me.ismartin.pokedexcompose.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.flow.MutableStateFlow
import me.ismartin.pokedexcompose.business.models.PokedexPokemon
import me.ismartin.pokedexcompose.ui.pokedex.PokedexScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AppScreens.PokemonListScreen.route
    ) {
        composable(AppScreens.PokemonListScreen.route) {
            val flow = MutableStateFlow(PagingData.from(emptyList<PokedexPokemon>()))
            PokedexScreen(pokemonList = flow.collectAsLazyPagingItems())
        }
    }
}
