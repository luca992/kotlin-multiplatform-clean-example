package co.lucaspinazzola.example.data.mapper

import co.lucaspinazzola.example.data.api.response.GiphySearchResponse
import co.lucaspinazzola.example.data.model.GifData
import co.lucaspinazzola.example.domain.model.Img
import co.lucaspinazzola.example.domain.utils.Date

class GifMapperImpl : GifMapper {


    override fun toDataModel(src: GiphySearchResponse.Data, index: Long) = GifData.Impl(
        id = src.id,
        resultIndex = index,
        url = src.images.original.url,
        urlWebp = src.images.original.webp,
        trendingDatetime = src.trendingDatetime.getTime()
    )

    override fun toDataModel(src: Array<GiphySearchResponse.Data>, offset: Long): List<GifData> =
        src.mapIndexed{ index, data ->  toDataModel(data, offset + index) }

    override fun toDomainModel(src: GifData) =
        Img(
            id = src.id,
            resultIndex = src.resultIndex,
            url = src.url,
            urlWebp = src.urlWebp,
            trendingDatetime = Date(src.trendingDatetime)
        )

    override fun toDomainModel(src: Array<GifData>): List<Img> =
        src.map { toDomainModel(it) }
}