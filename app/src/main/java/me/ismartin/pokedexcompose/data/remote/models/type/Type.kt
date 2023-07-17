package me.ismartin.pokedexcompose.data.remote.models.type

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class Type(
    @SerializedName("damage_relations")
    val damageRelations: JsonObject,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
)
