package me.ismartin.pokedexcompose

import java.io.InputStreamReader

object JsonUtils {

    fun readFileResource(filename: String): String {
        val inputStream = javaClass.classLoader?.getResourceAsStream(filename)
        val stringBuilder = StringBuilder()
        val reader = InputStreamReader(inputStream, "UTF-8")
        reader.readLines().forEach { line ->
            stringBuilder.append(line)
        }
        return stringBuilder.toString()
    }
}

object PokemonResponses {
    const val POKEMON_LIST_JSON = "pokeapi_pokemon_list_response.json"
    const val POKEMON_JSON = "pokeapi_pokemon_response.json"
    const val POKEMON_TYPE_LIST_JSON = "pokeapi_pokemon_type_list_response.json"
    const val POKEMON_TYPE_JSON = "pokeapi_pokemon_type_response.json"
    const val POKEMON_SPECIE_LIST_JSON = "pokeapi_pokemon_specie_list_response.json"
    const val POKEMON_SPECIE_JSON = "pokeapi_pokemon_specie_response.json"
}
