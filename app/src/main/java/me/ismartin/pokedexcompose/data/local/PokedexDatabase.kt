package me.ismartin.pokedexcompose.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import me.ismartin.pokedexcompose.data.local.daos.PokemonDao
import me.ismartin.pokedexcompose.data.local.daos.RemoteKeyDao
import me.ismartin.pokedexcompose.data.local.daos.TypeDao
import me.ismartin.pokedexcompose.data.local.entities.PokemonEntity
import me.ismartin.pokedexcompose.data.local.entities.RemoteKeyEntity
import me.ismartin.pokedexcompose.data.local.entities.TypeEntity

@Database(
    entities = [PokemonEntity::class, TypeEntity::class, RemoteKeyEntity::class],
    version = 1
)
abstract class PokedexDatabase : RoomDatabase() {

    abstract fun pokemonDao(): PokemonDao
    abstract fun typeDao(): TypeDao
    abstract fun remoteKeyDao(): RemoteKeyDao
}
