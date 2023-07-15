package me.ismartin.pokedexcompose

import kotlinx.coroutines.runBlocking
import me.ismartin.pokedexcompose.data.remote.PokeApiService
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
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
    fun `check empty pokedex`() = runBlocking {
        val mockResponse = MockResponse()
        mockResponse.setBody("{}")
        mockWebServer.enqueue(mockResponse)

        val response = pokeApiService.getPokemonList(0, 20)
        val request = mockWebServer.takeRequest()
        assertEquals("/pokemon?offset=0&limit=20", request.path)
        assertEquals("GET", request.method)

        assertTrue(response.isSuccessful)
        val pokemonList = response.body()?.results
        assertNull(pokemonList)
        assertEquals(0, response.body()?.count)
    }

    @Test
    fun `check pokedex with results`() = runBlocking {
        val mockResponse = MockResponse()
        val jsonResponse = JsonUtils.readFileResource(PokemonResponses.POKEMON_LIST_JSON)
        mockResponse.setBody(jsonResponse)
        mockWebServer.enqueue(mockResponse)

        val response = pokeApiService.getPokemonList(0, 20)
        val request = mockWebServer.takeRequest()
        assertEquals("/pokemon?offset=0&limit=20", request.path)
        assertEquals("GET", request.method)

        assertTrue(response.isSuccessful)
        val pokemonList = response.body()?.results
        assertNotNull(pokemonList)
        assertEquals(1281, response.body()?.count)
    }

    @Test
    fun `check empty pokemon`() = runBlocking {
        val mockResponse = MockResponse()
        mockResponse.setBody("{}")
        mockWebServer.enqueue(mockResponse)

        val response = pokeApiService.getPokemonById(4)
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
    fun `check pokemon with results`() = runBlocking {
        val mockResponse = MockResponse()
        val jsonResponse = JsonUtils.readFileResource(PokemonResponses.POKEMON_JSON)
        mockResponse.setBody(jsonResponse)
        mockWebServer.enqueue(mockResponse)

        val response = pokeApiService.getPokemonById(25)
        val request = mockWebServer.takeRequest()
        assertEquals("/pokemon/25", request.path)
        assertEquals("GET", request.method)

        assertTrue(response.isSuccessful)
        val pokemonId = response.body()?.id
        val pokemonName = response.body()?.name
        assertEquals(25, pokemonId)
        assertEquals("pikachu", pokemonName)
    }

    @Test
    fun `check pokemon type list with results`() = runBlocking {
        val mockResponse = MockResponse()
        val jsonResponse = JsonUtils.readFileResource(PokemonResponses.POKEMON_TYPE_LIST_JSON)
        mockResponse.setBody(jsonResponse)
        mockWebServer.enqueue(mockResponse)

        val response = pokeApiService.getTypeList()
        val request = mockWebServer.takeRequest()
        assertEquals("/type", request.path)
        assertEquals("GET", request.method)

        assertTrue(response.isSuccessful)
        val type = response.body()?.typeResults
        assertNotNull(type)
        assertEquals(20, response.body()?.count)
    }

    @Test
    fun `check pokemon type with results`() = runBlocking {
        val mockResponse = MockResponse()
        val jsonResponse = JsonUtils.readFileResource(PokemonResponses.POKEMON_TYPE_JSON)
        mockResponse.setBody(jsonResponse)
        mockWebServer.enqueue(mockResponse)

        val response = pokeApiService.getTypeById(10)
        val request = mockWebServer.takeRequest()
        assertEquals("/type/10", request.path)
        assertEquals("GET", request.method)

        assertTrue(response.isSuccessful)
        val typeId = response.body()?.id
        val typeName = response.body()?.name
        val doubleDamageToList = response.body()?.damageRelations?.doubleDamageTo
        assertEquals(10, typeId)
        assertEquals("fire", typeName)
        assertNotNull(doubleDamageToList)
        assertTrue(doubleDamageToList!!.isNotEmpty())
    }

    @Test
    fun `check pokemon stat list with results`() = runBlocking {
        val mockResponse = MockResponse()
        val jsonResponse = JsonUtils.readFileResource(PokemonResponses.POKEMON_STAT_LIST_JSON)
        mockResponse.setBody(jsonResponse)
        mockWebServer.enqueue(mockResponse)

        val response = pokeApiService.getStatList()
        val request = mockWebServer.takeRequest()
        assertEquals("/stat", request.path)
        assertEquals("GET", request.method)

        assertTrue(response.isSuccessful)
        val stats = response.body()?.results
        assertNotNull(stats)
        assertEquals(8, response.body()?.count)
        assertNull(response.body()?.next)
        assertNull(response.body()?.previous)
    }

    @Test
    fun `check pokemon stat with results`() = runBlocking {
        val mockResponse = MockResponse()
        val jsonResponse = JsonUtils.readFileResource(PokemonResponses.POKEMON_STAT_JSON)
        mockResponse.setBody(jsonResponse)
        mockWebServer.enqueue(mockResponse)

        val response = pokeApiService.getStatById(3)
        val request = mockWebServer.takeRequest()
        assertEquals("/stat/3", request.path)
        assertEquals("GET", request.method)

        assertTrue(response.isSuccessful)
        val statId = response.body()?.id
        val statName = response.body()?.name
        val statNames = response.body()?.names
        assertEquals(3, statId)
        assertEquals("defense", statName)
        assertNotNull(statNames)
        assertTrue(statNames!!.isNotEmpty())
    }

    @Test
    fun `check pokemon species list with results`() = runBlocking {
        val mockResponse = MockResponse()
        val jsonResponse = JsonUtils.readFileResource(PokemonResponses.POKEMON_SPECIE_LIST_JSON)
        mockResponse.setBody(jsonResponse)
        mockWebServer.enqueue(mockResponse)

        val response = pokeApiService.getPokemonSpecieList()
        val request = mockWebServer.takeRequest()
        assertEquals("/pokemon-species", request.path)
        assertEquals("GET", request.method)

        assertTrue(response.isSuccessful)
        val specieCount = response.body()?.count
        assertNotNull(specieCount)
        assertEquals(1010, specieCount)
        assertNotNull(response.body()?.next)
        assertNull(response.body()?.previous)
    }

    @Test
    fun `check pokemon specie with results`() = runBlocking {
        val mockResponse = MockResponse()
        val jsonResponse = JsonUtils.readFileResource(PokemonResponses.POKEMON_SPECIE_JSON)
        mockResponse.setBody(jsonResponse)
        mockWebServer.enqueue(mockResponse)

        val response = pokeApiService.getPokemonSpecieById(25)
        val request = mockWebServer.takeRequest()
        assertEquals("/pokemon-species/25", request.path)
        assertEquals("GET", request.method)

        assertTrue(response.isSuccessful)
        val pokemonId = response.body()?.id
        val pokemonName = response.body()?.name
        val pokemonColor = response.body()?.color?.name
        val isPokemonLegendary = response.body()?.isLegendary
        assertEquals(25, pokemonId)
        assertEquals("pikachu", pokemonName)
        assertNotNull(pokemonColor)
        assertEquals("yellow", pokemonColor!!)
        assertNotNull(isPokemonLegendary)
        assertFalse(isPokemonLegendary!!)
    }
}
