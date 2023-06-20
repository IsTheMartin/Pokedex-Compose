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
