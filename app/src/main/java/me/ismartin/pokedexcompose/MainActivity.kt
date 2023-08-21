package me.ismartin.pokedexcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import dagger.hilt.android.AndroidEntryPoint
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
                val viewModel: PokedexViewModel = hiltViewModel()
                val pokedex = viewModel.pokemonPaging.collectAsLazyPagingItems()
                PokedexScreen(pokemonList = pokedex)
            }
        }
    }
}
