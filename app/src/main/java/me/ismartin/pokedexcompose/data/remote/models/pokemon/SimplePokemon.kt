package me.ismartin.pokedexcompose.data.remote.models.pokemon

import com.google.gson.annotations.SerializedName

data class SimplePokemon(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)
