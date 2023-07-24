package me.ismartin.pokedexcompose.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "types")
data class TypeEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val damageRelation: String,
)
