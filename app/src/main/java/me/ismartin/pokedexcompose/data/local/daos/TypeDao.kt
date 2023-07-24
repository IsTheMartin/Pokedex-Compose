package me.ismartin.pokedexcompose.data.local.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import me.ismartin.pokedexcompose.data.local.entities.TypeEntity

@Dao
interface TypeDao {

    @Query("SELECT * FROM types WHERE id=:id")
    fun getTypeById(id: Int): TypeEntity

    @Query("SELECT * FROM types WHERE name=:name")
    fun getTypeByName(name: String): TypeEntity

    @Query("SELECT COUNT(*) FROM types")
    fun getTypesCount(): Int

    @Upsert
    fun upsertTypes(types: List<TypeEntity>)

    @Query("DELETE FROM types")
    fun deleteTypes()
}
