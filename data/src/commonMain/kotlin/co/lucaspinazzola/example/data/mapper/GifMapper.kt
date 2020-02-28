package co.lucaspinazzola.example.data.mapper

import co.lucaspinazzola.example.data.api.response.GiphySearchResponse
import co.lucaspinazzola.example.data.model.GifData
import co.lucaspinazzola.example.domain.model.Gif

interface GifMapper {
    fun toDomainModel(src: GifData): Gif
    fun toDomainModel(src: Array<GifData>): List<Gif>
    fun toDataModel(src: GiphySearchResponse.Data): GifData
    fun toDataModel(src: Array<GiphySearchResponse.Data>): List<GifData>

}