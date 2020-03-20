package co.lucaspinazzola.example.data.mapper

import co.lucaspinazzola.example.data.api.giphy.response.GiphySearchResponse
import co.lucaspinazzola.example.data.api.rickandmorty.response.RickAndMortySearchResponse
import co.lucaspinazzola.example.data.model.ImgData
import co.lucaspinazzola.example.domain.model.Img

interface ImgMapper {
    fun toDomainModel(src: ImgData): Img
    fun toDomainModel(src: Array<ImgData>): List<Img>
    fun toDataModel(src: GiphySearchResponse.Data, index: Long): ImgData
    fun toDataModel(src: Array<GiphySearchResponse.Data>, offset: Long): List<ImgData>
    fun toDataModel(src: RickAndMortySearchResponse.Result): ImgData
    fun toDataModel(src: Array<RickAndMortySearchResponse.Result>): List<ImgData>

}