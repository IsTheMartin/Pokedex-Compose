package me.ismartin.pokedexcompose.utils

fun String.getId(): Int {
    val regex = Regex("/(\\d+)/")
    val regexResult = regex.find(this)
    return regexResult?.groupValues?.lastOrNull()?.toInt() ?: -1
}
