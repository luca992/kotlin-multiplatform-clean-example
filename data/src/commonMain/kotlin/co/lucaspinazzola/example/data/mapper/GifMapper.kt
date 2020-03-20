package co.lucaspinazzola.example.data.mapper

import co.lucaspinazzola.example.data.api.response.GiphySearchResponse
import co.lucaspinazzola.example.data.model.GifData
import co.lucaspinazzola.example.domain.model.Img

interface GifMapper {
    fun toDomainModel(src: GifData): Img
    fun toDomainModel(src: Array<GifData>): List<Img>
    fun toDataModel(src: GiphySearchResponse.Data, index: Long): GifData
    fun toDataModel(src: Array<GiphySearchResponse.Data>, offset: Long): List<GifData>

}