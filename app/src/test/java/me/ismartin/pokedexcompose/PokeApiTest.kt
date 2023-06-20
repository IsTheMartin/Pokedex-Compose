package me.ismartin.pokedexcompose

import me.ismartin.pokedexcompose.data.remote.PokeApiService
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class PokeApiTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var pokeApiService: PokeApiService

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        pokeApiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `check empty pokedex`() {
        val mockResponse = MockResponse()
        mockResponse.setBody("{}")
        mockWebServer.enqueue(mockResponse)

        val response = pokeApiService.getPokemonList(0, 20).execute()
        val request = mockWebServer.takeRequest()
        assertEquals("/pokemon?offset=0&limit=20", request.path)
        assertEquals("GET", request.method)

        assertTrue(response.isSuccessful)
        val pokemonList = response.body()?.results
        assertNull(pokemonList)
        assertEquals(0, response.body()?.count)
    }

    @Test
    fun `check pokedex with results`() {
        val mockResponse = MockResponse()
        val jsonResponse = JsonUtils.readFileResource("pokeapi_response.json")
        mockResponse.setBody(jsonResponse)
        mockWebServer.enqueue(mockResponse)

        val response = pokeApiService.getPokemonList(0, 20).execute()
        val request = mockWebServer.takeRequest()
        assertEquals("/pokemon?offset=0&limit=20", request.path)
        assertEquals("GET", request.method)

        assertTrue(response.isSuccessful)
        val pokemonList = response.body()?.results
        assertNotNull(pokemonList)
        assertEquals(1281, response.body()?.count)
    }

    @Test
    fun `check empty pokemon`() {
        val mockResponse = MockResponse()
        mockResponse.setBody("{}")
        mockWebServer.enqueue(mockResponse)

        val response = pokeApiService.getPokemonById(4).execute()
        val request = mockWebServer.takeRequest()
        assertEquals("/pokemon/4", request.path)
        assertEquals("GET", request.method)

        assertTrue(response.isSuccessful)
        val pokemonId = response.body()?.id
        val pokemonName = response.body()?.name
        assertEquals(0, pokemonId)
        assertNull(pokemonName)
    }

    @Test
    fun `check pokemon with results`() {
        val mockResponse = MockResponse()
        val jsonResponse = JsonUtils.readFileResource("pokeapi_pokemon_response.json")
        mockResponse.setBody(jsonResponse)
        mockWebServer.enqueue(mockResponse)

        val response = pokeApiService.getPokemonById(25).execute()
        val request = mockWebServer.takeRequest()
        assertEquals("/pokemon/25", request.path)
        assertEquals("GET", request.method)

        assertTrue(response.isSuccessful)
        val pokemonId = response.body()?.id
        val pokemonName = response.body()?.name
        assertEquals(25, pokemonId)
        assertEquals("pikachu", pokemonName)
    }
}
