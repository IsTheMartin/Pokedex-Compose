package me.ismartin.pokedexcompose.data.remote.models.type

import com.google.gson.annotations.SerializedName

data class Name(
    @SerializedName("language")
    val language: Language,
    @SerializedName("name")
    val name: String
)
