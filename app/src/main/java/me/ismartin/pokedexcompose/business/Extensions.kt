package me.ismartin.pokedexcompose.business

fun Int.formatPokemonId(): String {
    return String.format("%03d", this)
}
