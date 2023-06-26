package me.ismartin.pokedexcompose.data.remote.models.stat

import com.google.gson.annotations.SerializedName

data class Stat(
    @SerializedName("game_index")
    val gameIndex: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("is_battle_only")
    val isBattleOnly: Boolean,
    @SerializedName("name")
    val name: String,
    @SerializedName("names")
    val names: List<Name>
)
