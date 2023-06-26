package me.ismartin.pokedexcompose.data.remote.models.pokemon

import com.google.gson.annotations.SerializedName

data class Move(
    @SerializedName("move")
    val move: SubMove,
    @SerializedName("version_group_details")
    val versionGroupDetails: List<VersionGroupDetail>
)
