package co.lucaspinazzola.example.data.api

data class MockResponse(
    val endpointSegment: String,
    val responseBody: String,
    val httpStatusCode: Int = 200
)