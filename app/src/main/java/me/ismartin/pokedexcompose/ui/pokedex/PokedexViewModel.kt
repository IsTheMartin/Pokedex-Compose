package me.ismartin.pokedexcompose.ui.pokedex

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.ismartin.pokedexcompose.business.PokedexMediator
import javax.inject.Inject

@HiltViewModel
class PokedexViewModel @Inject constructor(
    private val pokedexMediator: PokedexMediator
) : ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            pokedexMediator.downloadAndSave()
        }
    }
}
