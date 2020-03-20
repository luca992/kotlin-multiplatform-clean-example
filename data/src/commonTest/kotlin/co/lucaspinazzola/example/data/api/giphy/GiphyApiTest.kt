package co.lucaspinazzola.example.data.api.giphy

import co.lucaspinazzola.example.data.api.ApiMockEngine

abstract class GiphyApiTest {

    protected val apiMockEngine = ApiMockEngine()

    protected val businessId = 1L

    protected val BASE_URL = "https://api.giphy.com"

    protected fun givenAMockTodoApiClient(
            endpointSegment: String,
            responseBody: String = "",
            httpStatusCode: Int = 200
    ): GiphyApi {
        apiMockEngine.enqueueMockResponse(endpointSegment, responseBody, httpStatusCode)
        return GiphyApi("mock_key", true)
            .apply { otherEngine = apiMockEngine.get() }
    }
}