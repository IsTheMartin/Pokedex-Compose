package me.ismartin.pokedexcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import me.ismartin.pokedexcompose.ui.navigation.AppNavigation
import me.ismartin.pokedexcompose.ui.splash.SplashViewModel
import me.ismartin.pokedexcompose.ui.theme.PokedexComposeTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setupSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            PokedexComposeTheme {
                // A surface container using the 'background' color from the theme
                AppNavigation()
            }
        }
    }

    private fun setupSplashScreen() {
        val splashViewModel: SplashViewModel by viewModels()
        var keepSplashScreenOn = true
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                splashViewModel.splashState.collect { state ->
                    println("MRTN > MainActivity.setupSplashScreen > Hey! state: ${state.status}")
                    keepSplashScreenOn = state.status != "Loading" && state.simplePokemon.isEmpty()
                }
            }
        }

        installSplashScreen().setKeepOnScreenCondition {
            keepSplashScreenOn
        }
    }
}
