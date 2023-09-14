package me.ismartin.pokedexcompose.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import me.ismartin.pokedexcompose.data.local.entities.PokemonStatsEntity
import me.ismartin.pokedexcompose.data.local.entities.PokemonTypesEntity

class PokedexConverters {

    @TypeConverter
    fun fromTypes(typesEntity: List<PokemonTypesEntity>): String {
        return Gson().toJson(typesEntity)
    }

    @TypeConverter
    fun toTypes(types: String): List<PokemonTypesEntity> {
        val type = object : TypeToken<List<PokemonTypesEntity>>() {}.type
        return Gson().fromJson(types, type)
    }

    @TypeConverter
    fun fromStats(statsEntity: List<PokemonStatsEntity>): String {
        return Gson().toJson(statsEntity)
    }

    @TypeConverter
    fun toStats(stats: String): List<PokemonStatsEntity> {
        val type = object : TypeToken<List<PokemonStatsEntity>>() {}.type
        return Gson().fromJson(stats, type)
    }
}
