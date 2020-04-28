package co.lucaspinazzola.example.data.api.giphy

import co.lucaspinazzola.example.data.api.eLogger
import co.lucaspinazzola.example.data.api.giphy.response.GiphySearchResponse
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.utils.io.core.use
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration

class GiphyApi(var apiKey: String,
               private val enableLogging: Boolean) {

    private val url = "https://api.giphy.com"

    var otherEngine: HttpClientEngine? = null

    val json = Json(
            configuration = JsonConfiguration.Default.copy(
                    ignoreUnknownKeys = true,
                    isLenient = true,
                    encodeDefaults = false
            )
    )

    private val serializer = KotlinxSerializer(json)

    private val client get() = if (otherEngine!=null) HttpClient(otherEngine!!, httpClientConfig) else HttpClient(httpClientConfig)


    private val httpClientConfig: HttpClientConfig<*>.() -> Unit = {

        if (enableLogging) {
            install(Logging) {
                logger = eLogger
                level = LogLevel.ALL
            }
        }

        install(JsonFeature) {
            serializer = this@GiphyApi.serializer
        }


    }


    /***
     * @param query: Search query term or phrase.
     * @param offset: Position in pagination.
     */
    suspend fun searchGifs(query: String, offset: Long, limit: Int = 10) : GiphySearchResponse =
            client.use {
                it.get("$url/v1/gifs/search"){
                    parameter(GiphyApiConstants.API_KEY, apiKey)
                    parameter(GiphyApiConstants.LIMIT, limit)
                    parameter(GiphyApiConstants.OFFSET, offset)
                    parameter(GiphyApiConstants.QUERY,query)
                }
            }

}