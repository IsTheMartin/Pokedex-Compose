package me.ismartin.pokedexcompose.data.remote.models.stat

import com.google.gson.annotations.SerializedName

data class StatPageResult(
    @SerializedName("count")
    val count: Int,
    @SerializedName("next")
    val next: Any,
    @SerializedName("previous")
    val previous: Any,
    @SerializedName("results")
    val results: List<StatResult>
)
