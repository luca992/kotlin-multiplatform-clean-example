package co.lucaspinazzola.example.data.api.rickandmorty

import co.lucaspinazzola.example.data.api.ApiMockEngine

abstract class RickAndMortyApiTest {

    protected val apiMockEngine = ApiMockEngine()

    protected fun givenAMockTodoApiClient(
            endpointSegment: String,
            responseBody: String = "",
            httpStatusCode: Int = 200
    ): RickAndMortyApi {
        apiMockEngine.enqueueMockResponse(endpointSegment, responseBody, httpStatusCode)
        return RickAndMortyApi( true)
            .apply { otherEngine = apiMockEngine.get() }
    }
}