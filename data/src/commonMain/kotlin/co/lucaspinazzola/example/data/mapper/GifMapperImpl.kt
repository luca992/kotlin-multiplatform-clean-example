package co.lucaspinazzola.example.data.mapper

import co.lucaspinazzola.example.data.api.giphy.response.GiphySearchResponse
import co.lucaspinazzola.example.data.api.rickandmorty.response.RickAndMortySearchResponse
import co.lucaspinazzola.example.data.model.ImgData
import co.lucaspinazzola.example.domain.model.Img

class GifMapperImpl : GifMapper {


    override fun toDataModel(src: GiphySearchResponse.Data, index: Long) = ImgData.Impl(
        id = src.id,
        resultIndex = index,
        url = src.images.original.webp
    )

    override fun toDataModel(src: Array<GiphySearchResponse.Data>, offset: Long): List<ImgData> =
        src.mapIndexed{ index, data ->  toDataModel(data, offset + index) }

    override fun toDomainModel(src: ImgData) =
        Img(
            id = src.id,
            resultIndex = src.resultIndex,
            url = src.url
        )

    override fun toDomainModel(src: Array<ImgData>): List<Img> =
        src.map { toDomainModel(it) }

    override fun toDataModel(src: Array<RickAndMortySearchResponse.Result>, offset: Long): List<ImgData> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun toDataModel(src: RickAndMortySearchResponse.Result, index: Long): ImgData {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}