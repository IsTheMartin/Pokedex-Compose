package me.ismartin.pokedexcompose.data.remote.models.specie

import com.google.gson.annotations.SerializedName

data class Color(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)
