package me.ismartin.pokedexcompose

import me.ismartin.pokedexcompose.utils.getId
import org.junit.Assert.assertEquals
import org.junit.Test

class ExtensionsTest {

    @Test
    fun `Correct pokemon id from url`() {
        val pokemonUrl = "https://pokeapi.co/api/v2/pokemon/19/"
        val pokemonId = pokemonUrl.getId()
        assertEquals(19, pokemonId)
    }

    @Test
    fun `No pokemon id in url`() {
        val pokemonUrl = "https://pokeapi.co/api/v2/pokemon/"
        val pokemonId = pokemonUrl.getId()
        assertEquals(-1, pokemonId)
    }
}
