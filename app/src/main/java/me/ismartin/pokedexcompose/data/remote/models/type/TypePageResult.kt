package me.ismartin.pokedexcompose.data.remote.models.type

import com.google.gson.annotations.SerializedName

data class TypePageResult(
    @SerializedName("count")
    val count: Int,
    @SerializedName("next")
    val next: Any,
    @SerializedName("previous")
    val previous: Any,
    @SerializedName("results")
    val typeResults: List<TypeResult>
)
