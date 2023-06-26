package me.ismartin.pokedexcompose.data.remote.models.type

import com.google.gson.annotations.SerializedName

data class HalfDamageTo(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)
