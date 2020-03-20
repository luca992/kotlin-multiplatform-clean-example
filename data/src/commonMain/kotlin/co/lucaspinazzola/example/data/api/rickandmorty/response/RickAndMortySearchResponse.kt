package co.lucaspinazzola.example.data.api.rickandmorty.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class RickAndMortySearchResponse(
    @SerialName("info") val info: Info,
    @SerialName("results") val results: List<Result>
) {
    @Serializable
    data class Result(
        @SerialName("id") val id: String,
        @SerialName("name") val name: String,
        @SerialName("image") val image: String
    )
    @Serializable
    data class Info(
        @SerialName("count") val count: Int,
        @SerialName("pages") val pages: Int,
        @SerialName("total_count") val totalCount: Int
    )
}
