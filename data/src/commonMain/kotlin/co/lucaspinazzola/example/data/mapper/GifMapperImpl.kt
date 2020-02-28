package co.lucaspinazzola.example.data.mapper

import co.lucaspinazzola.example.data.api.response.GiphySearchResponse
import co.lucaspinazzola.example.data.model.GifData
import co.lucaspinazzola.example.domain.model.Gif
import co.lucaspinazzola.example.domain.utils.Date

class GifMapperImpl : GifMapper {


    override fun toDataModel(src: GiphySearchResponse.Data) = GifData.Impl(
        id = src.id,
        url = src.images.original.url,
        urlWebp = src.images.original.webp,
        trendingDatetime = src.trendingDatetime.getTime()
    )

    override fun toDataModel(src: Array<GiphySearchResponse.Data>): List<GifData> =
        src.map { toDataModel(it) }

    override fun toDomainModel(src: GifData) =
        Gif(
            id = src.id,
            url = src.url,
            urlWebp = src.urlWebp,
            trendingDatetime = Date(src.trendingDatetime)
        )

    override fun toDomainModel(src: Array<GifData>): List<Gif> =
        src.map { toDomainModel(it) }
}