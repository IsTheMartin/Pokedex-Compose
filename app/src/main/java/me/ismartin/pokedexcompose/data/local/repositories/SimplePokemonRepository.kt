package me.ismartin.pokedexcompose.data.local.repositories

import me.ismartin.pokedexcompose.data.local.daos.SimplePokemonDao
import me.ismartin.pokedexcompose.data.local.entities.SimplePokemonEntity
import javax.inject.Inject

interface SimplePokemonRepository {
    fun getSimplePokemonCount(): Int
    fun getSimplePokemonList(): List<SimplePokemonEntity>
    suspend fun insertSimplePokemon(pokemonList: List<SimplePokemonEntity>)
}

class SimplePokemonRepositoryImpl @Inject constructor(
    private val simplePokemonDao: SimplePokemonDao
) : SimplePokemonRepository {
    override fun getSimplePokemonCount(): Int {
        return simplePokemonDao.getSimplePokemonCount()
    }

    override fun getSimplePokemonList(): List<SimplePokemonEntity> {
        return simplePokemonDao.getAllSimplePokemon()
    }

    override suspend fun insertSimplePokemon(pokemonList: List<SimplePokemonEntity>) {
        simplePokemonDao.upsertSimplePokemons(pokemonList)
    }
}
