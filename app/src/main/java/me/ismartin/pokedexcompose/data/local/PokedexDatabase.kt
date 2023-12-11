package me.ismartin.pokedexcompose.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import me.ismartin.pokedexcompose.data.local.daos.PokemonDao
import me.ismartin.pokedexcompose.data.local.daos.RemoteKeyDao
import me.ismartin.pokedexcompose.data.local.daos.SimplePokemonDao
import me.ismartin.pokedexcompose.data.local.daos.TypeDao
import me.ismartin.pokedexcompose.data.local.entities.PokemonEntity
import me.ismartin.pokedexcompose.data.local.entities.RemoteKeyEntity
import me.ismartin.pokedexcompose.data.local.entities.SimplePokemonEntity
import me.ismartin.pokedexcompose.data.local.entities.TypeEntity

@Database(
    entities = [
        PokemonEntity::class,
        TypeEntity::class,
        RemoteKeyEntity::class,
        SimplePokemonEntity::class,
    ],
    version = 1
)
@TypeConverters(
    PokedexConverters::class
)
abstract class PokedexDatabase : RoomDatabase() {

    abstract fun pokemonDao(): PokemonDao
    abstract fun typeDao(): TypeDao
    abstract fun remoteKeyDao(): RemoteKeyDao
    abstract fun simplePokemonDao(): SimplePokemonDao
}
