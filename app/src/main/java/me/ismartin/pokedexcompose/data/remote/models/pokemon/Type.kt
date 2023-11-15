package me.ismartin.pokedexcompose.data.remote.models.pokemon

import com.google.gson.annotations.SerializedName

data class Type(
    @SerializedName("slot")
    val slot: Int,
    @SerializedName("type")
    val type: TypeName
)

data class TypeName(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String,
)
