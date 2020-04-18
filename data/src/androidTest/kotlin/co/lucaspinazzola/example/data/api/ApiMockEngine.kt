package co.lucaspinazzola.example.data.api

import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.request.HttpRequestData
import io.ktor.content.TextContent
import io.ktor.http.*
import kotlin.test.assertEquals

class ApiMockEngine {
    private lateinit var mockResponse: MockResponse

    private var lastRequestData: HttpRequestData? = null

    fun enqueueMockResponse(
            endpointSegment: String,
            responseBody: String,
            httpStatusCode: Int = 200
    ) {
        mockResponse = MockResponse(endpointSegment, responseBody, httpStatusCode)
    }

    fun get() = MockEngine.create {
        addHandler { request : HttpRequestData ->
            lastRequestData = request

            when (request.url.encodedPath) {
                "${mockResponse.endpointSegment}" -> {
                    respond(
                            content = mockResponse.responseBody,
                            status = HttpStatusCode.fromValue(mockResponse.httpStatusCode),
                            headers = headersOf(HttpHeaders.ContentType to listOf(ContentType.Application.Json.toString()))
                    )
                }
                else -> {
                    error("Unhandled ${mockResponse.endpointSegment}  -  ${request.url.fullPath}")
                }
            }
        }
    }

    fun verifyRequestContainsHeader(key: String, expectedValue: String) {
        val value = lastRequestData!!.headers[key]
        assertEquals(expectedValue, value)
    }


    fun verifyRequestContentType(expectedValue: String) {
        val value = "${lastRequestData!!.body.contentType}/${lastRequestData!!.body.contentType?.contentSubtype}"
        assertEquals(expectedValue, value)
    }

    fun verifyRequestBody(addTaskRequest: String) {
        val body = (lastRequestData!!.body as TextContent).text

        assertEquals(addTaskRequest, body)
    }

    fun verifyGetRequest() {
        assertEquals(HttpMethod.Get.value, lastRequestData!!.method.value)
    }

    fun verifyPostRequest() {
        assertEquals(HttpMethod.Post.value, lastRequestData!!.method.value)
    }

    fun verifyPutRequest() {
        assertEquals(HttpMethod.Put.value, lastRequestData!!.method.value)
    }

    fun verifyDeleteRequest() {
        assertEquals(HttpMethod.Delete.value, lastRequestData!!.method.value)
    }
}