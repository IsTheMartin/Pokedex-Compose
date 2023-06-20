package me.ismartin.pokedexcompose.data.remote.models.pokemon

import com.google.gson.annotations.SerializedName

data class PokemonType(
    @SerializedName("slot")
    val slot: Int,
    @SerializedName("type")
    val type: SubType
)
