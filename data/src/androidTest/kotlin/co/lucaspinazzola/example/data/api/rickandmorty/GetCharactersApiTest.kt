package co.lucaspinazzola.example.data.api.rickandmorty

import co.lucaspinazzola.example.data.api.rickandmorty.response.getCharactersResponse
import co.lucaspinazzola.example.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetCharactersApiTest : RickAndMortyApiTest() {
    private val PATH = "/api/character"

    @BeforeTest
    fun setUp() {
    }


    @Test
    fun `get returns 20 characters`() = runTest {
        val apiClient = givenAMockTodoApiClient(PATH,
            getCharactersResponse
        )
        val response = apiClient.getCharacters(0)
        apiMockEngine.verifyRequestContainsHeader("Accept", "application/json")
        assertEquals(20, response.results.size)
    }



    @Test
    fun `first character is rick`() = runTest {
        val apiClient = givenAMockTodoApiClient(PATH,
            getCharactersResponse
        )
        val response = apiClient.getCharacters(0)
        apiMockEngine.verifyRequestContainsHeader("Accept", "application/json")
        assertEquals("Rick Sanchez", response.results[0].name)
    }

    @Test
    fun `last character is ants in my eyes johnson`() = runTest {
        val apiClient = givenAMockTodoApiClient(PATH,
            getCharactersResponse
        )
        val response = apiClient.getCharacters(0)
        apiMockEngine.verifyRequestContainsHeader("Accept", "application/json")
        assertEquals("Ants in my Eyes Johnson", response.results[19].name)
    }
}