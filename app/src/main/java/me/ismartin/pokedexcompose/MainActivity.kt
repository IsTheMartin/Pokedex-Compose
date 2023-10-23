package me.ismartin.pokedexcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import dagger.hilt.android.AndroidEntryPoint
import me.ismartin.pokedexcompose.ui.PokedexNavigation
import me.ismartin.pokedexcompose.ui.pokedex.PokedexScreen
import me.ismartin.pokedexcompose.ui.pokedex.PokedexViewModel
import me.ismartin.pokedexcompose.ui.theme.PokedexComposeTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokedexComposeTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()
                val viewModel: PokedexViewModel = hiltViewModel()
                val pokedex = viewModel.pokemonPaging.collectAsLazyPagingItems()
                NavHost(
                    navController = navController,
                    startDestination = PokedexNavigation.PokedexScreen.route
                ) {
                    composable(route = PokedexNavigation.PokedexScreen.route) {
                        PokedexScreen(pokemonList = pokedex)
                    }
                }
            }
        }
    }
}
