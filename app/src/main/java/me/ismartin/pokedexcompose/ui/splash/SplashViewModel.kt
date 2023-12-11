package me.ismartin.pokedexcompose.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.ismartin.pokedexcompose.usecases.LoadSimplePokemonUseCase
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val loadSimplePokemonUseCase: LoadSimplePokemonUseCase
) : ViewModel() {

    private val _splashState = MutableStateFlow(SplashState())
    val splashState: StateFlow<SplashState> get() = _splashState

    init {
        loadSimplePokemonInformation()
    }

    private fun loadSimplePokemonInformation() {
        viewModelScope.launch(Dispatchers.IO) {
            loadSimplePokemonUseCase.invoke()
                .collect { list ->
                    if (list.isNotEmpty()) {
                        _splashState.update { state ->
                            state.copy(
                                status = "Loaded",
                                simplePokemon = list
                            )
                        }
                    } else {
                        _splashState.update { state ->
                            state.copy(
                                status = "Error"
                            )
                        }
                    }
                }
        }
    }
}
