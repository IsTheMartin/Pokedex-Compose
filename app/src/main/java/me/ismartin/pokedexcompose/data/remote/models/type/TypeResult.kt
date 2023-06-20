package me.ismartin.pokedexcompose.data.remote.models.type

import com.google.gson.annotations.SerializedName

data class TypeResult(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)
