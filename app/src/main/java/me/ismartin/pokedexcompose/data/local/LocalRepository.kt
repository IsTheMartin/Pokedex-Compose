package me.ismartin.pokedexcompose.data.local

import androidx.paging.PagingSource
import me.ismartin.pokedexcompose.data.local.daos.PokemonDao
import me.ismartin.pokedexcompose.data.local.daos.RemoteKeyDao
import me.ismartin.pokedexcompose.data.local.daos.SimplePokemonDao
import me.ismartin.pokedexcompose.data.local.daos.TypeDao
import me.ismartin.pokedexcompose.data.local.entities.PokemonEntity
import me.ismartin.pokedexcompose.data.local.entities.RemoteKeyEntity
import me.ismartin.pokedexcompose.data.local.entities.SimplePokemonEntity
import me.ismartin.pokedexcompose.data.local.entities.TypeEntity
import javax.inject.Inject

interface LocalRepository {
    fun getPokemonCount(): Int
    fun getPagedPokedex(): PagingSource<Int, PokemonEntity>
    fun insertPokemons(pokemons: List<PokemonEntity>)
    fun deletePokemons()
    fun getTypesCount(): Int
    fun insertTypes(types: List<TypeEntity>)
    suspend fun getRemoteKeyById(id: String): RemoteKeyEntity?
    suspend fun getSimplePokemon(): List<SimplePokemonEntity>
    suspend fun insertSimplePokemons(simplePokemons: List<SimplePokemonEntity>)
}

class LocalRepositoryImpl @Inject constructor(
    private val pokemonDao: PokemonDao,
    private val typeDao: TypeDao,
    private val remoteKeyDao: RemoteKeyDao,
    private val simplePokemonDao: SimplePokemonDao
) : LocalRepository {

    override fun getPokemonCount(): Int {
        return pokemonDao.getPokemonCount()
    }

    override fun getPagedPokedex(): PagingSource<Int, PokemonEntity> {
        return pokemonDao.getPagedPokedex()
    }

    override fun insertPokemons(pokemons: List<PokemonEntity>) {
        return pokemonDao.upsertPokemons(pokemons)
    }

    override fun deletePokemons() {
        return pokemonDao.deletePokemons()
    }

    override fun getTypesCount(): Int {
        return typeDao.getTypesCount()
    }

    override fun insertTypes(types: List<TypeEntity>) {
        typeDao.upsertTypes(types)
    }

    override suspend fun getRemoteKeyById(id: String): RemoteKeyEntity? {
        return remoteKeyDao.getById(id)
    }

    override suspend fun getSimplePokemon(): List<SimplePokemonEntity> {
        return simplePokemonDao.getAllSimplePokemon()
    }

    override suspend fun insertSimplePokemons(simplePokemons: List<SimplePokemonEntity>) {
        simplePokemonDao.upsertSimplePokemons(simplePokemons)
    }
}
