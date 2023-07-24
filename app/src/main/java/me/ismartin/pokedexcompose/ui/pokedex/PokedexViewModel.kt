package me.ismartin.pokedexcompose.ui.pokedex

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import me.ismartin.pokedexcompose.data.local.entities.PokemonEntity
import javax.inject.Inject

@HiltViewModel
class PokedexViewModel @Inject constructor(
    pager: Pager<Int, PokemonEntity>
) : ViewModel() {

    val pokemonPaging = pager
        .flow
        .map { pagingData ->
            pagingData.map { it }
        }
        .cachedIn(viewModelScope)
}
