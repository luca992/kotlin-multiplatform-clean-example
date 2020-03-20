package co.lucaspinazzola.example.data.api.giphy

import co.lucaspinazzola.example.data.api.giphy.response.gifSearchResponse
import co.lucaspinazzola.example.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class SearchGifsApiTest : GiphyApiTest() {
    private val SEARCH_PATH = "/v1/gifs/search"

    @BeforeTest
    fun setUp() {
    }


    @Test
    fun `search returns 2 dog gifs`() = runTest {
        val apiClient = givenAMockTodoApiClient(SEARCH_PATH,
            gifSearchResponse
        )
        val response = apiClient.searchGifs("dogs", 0, 2)
        apiMockEngine.verifyRequestContainsHeader("Accept", "application/json")
        assertEquals(2, response.data.size)
    }

}