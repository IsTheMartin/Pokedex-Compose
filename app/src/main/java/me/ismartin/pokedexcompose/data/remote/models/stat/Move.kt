package me.ismartin.pokedexcompose.data.remote.models.stat

import com.google.gson.annotations.SerializedName

data class Move(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)
