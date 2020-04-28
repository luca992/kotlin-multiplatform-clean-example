package co.lucaspinazzola.example.data.api.giphy.response


import co.lucaspinazzola.example.data.utils.DateSerializer
import co.lucaspinazzola.example.domain.utils.Date
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GiphySearchResponse(
    @SerialName("data") val data: List<Data>,
    @SerialName("meta") val meta: Meta,
    @SerialName("pagination") val pagination: Pagination
) {
    @Serializable
    data class Data(
        @SerialName("analytics") val analytics: Analytics,
        @SerialName("analytics_response_payload") val analyticsResponsePayload: String,
        @SerialName("bitly_gif_url") val bitlyGifUrl: String,
        @SerialName("bitly_url") val bitlyUrl: String,
        @SerialName("content_url") val contentUrl: String,
        @SerialName("embed_url") val embedUrl: String,
        @SerialName("id") val id: String,
        @SerialName("images") val images: Images,
        @SerialName("import_datetime") val importDatetime: String,
        @SerialName("is_sticker") val isSticker: Int,
        @SerialName("rating") val rating: String,
        @SerialName("slug") val slug: String,
        @SerialName("source") val source: String,
        @SerialName("source_post_url") val sourcePostUrl: String,
        @SerialName("source_tld") val sourceTld: String,
        @SerialName("title") val title: String,
        @Serializable(with= DateSerializer::class) @SerialName("trending_datetime") val trendingDatetime: Date,
        @SerialName("type") val type: String,
        @SerialName("url") val url: String,
        @SerialName("username") val username: String
    ) {
        @Serializable
        data class Analytics(
            @SerialName("onclick") val onclick: Onclick,
            @SerialName("onload") val onload: Onload,
            @SerialName("onsent") val onsent: Onsent
        ) {
            @Serializable
            data class Onclick(
                @SerialName("url") val url: String
            )

            @Serializable
            data class Onload(
                @SerialName("url") val url: String
            )

            @Serializable
            data class Onsent(
                @SerialName("url") val url: String
            )
        }

        @Serializable
        data class Images(
            @SerialName("downsized") val downsized: Downsized,
            @SerialName("downsized_large") val downsizedLarge: DownsizedLarge,
            @SerialName("downsized_medium") val downsizedMedium: DownsizedMedium,
            @SerialName("downsized_small") val downsizedSmall: DownsizedSmall,
            @SerialName("downsized_still") val downsizedStill: DownsizedStill,
            @SerialName("fixed_height") val fixedHeight: FixedHeight,
            @SerialName("fixed_height_downsampled") val fixedHeightDownsampled: FixedHeightDownsampled,
            @SerialName("fixed_height_small") val fixedHeightSmall: FixedHeightSmall,
            @SerialName("fixed_height_small_still") val fixedHeightSmallStill: FixedHeightSmallStill,
            @SerialName("fixed_height_still") val fixedHeightStill: FixedHeightStill,
            @SerialName("fixed_width") val fixedWidth: FixedWidth,
            @SerialName("fixed_width_downsampled") val fixedWidthDownsampled: FixedWidthDownsampled,
            @SerialName("fixed_width_small") val fixedWidthSmall: FixedWidthSmall,
            @SerialName("fixed_width_small_still") val fixedWidthSmallStill: FixedWidthSmallStill,
            @SerialName("fixed_width_still") val fixedWidthStill: FixedWidthStill,
            @SerialName("looping") val looping: Looping,
            @SerialName("original") val original: Original,
            @SerialName("original_mp4") val originalMp4: OriginalMp4,
            @SerialName("original_still") val originalStill: OriginalStill,
            @SerialName("preview") val preview: Preview,
            @SerialName("preview_gif") val previewGif: PreviewGif,
            @SerialName("preview_webp") val previewWebp: PreviewWebp,
            @SerialName("480w_still") val wStill: WStill
        ) {
            @Serializable
            data class Downsized(
                @SerialName("height") val height: String,
                @SerialName("size") val size: String,
                @SerialName("url") val url: String,
                @SerialName("width") val width: String
            )

            @Serializable
            data class DownsizedLarge(
                @SerialName("height") val height: String,
                @SerialName("size") val size: String,
                @SerialName("url") val url: String,
                @SerialName("width") val width: String
            )

            @Serializable
            data class DownsizedMedium(
                @SerialName("height") val height: String,
                @SerialName("size") val size: String,
                @SerialName("url") val url: String,
                @SerialName("width") val width: String
            )

            @Serializable
            data class DownsizedSmall(
                @SerialName("height") val height: String,
                @SerialName("mp4") val mp4: String,
                @SerialName("mp4_size") val mp4Size: String,
                @SerialName("width") val width: String
            )

            @Serializable
            data class DownsizedStill(
                @SerialName("height") val height: String,
                @SerialName("size") val size: String,
                @SerialName("url") val url: String,
                @SerialName("width") val width: String
            )

            @Serializable
            data class FixedHeight(
                @SerialName("height") val height: String,
                @SerialName("mp4") val mp4: String,
                @SerialName("mp4_size") val mp4Size: String,
                @SerialName("size") val size: String,
                @SerialName("url") val url: String,
                @SerialName("webp") val webp: String,
                @SerialName("webp_size") val webpSize: String,
                @SerialName("width") val width: String
            )

            @Serializable
            data class FixedHeightDownsampled(
                @SerialName("height") val height: String,
                @SerialName("size") val size: String,
                @SerialName("url") val url: String,
                @SerialName("webp") val webp: String,
                @SerialName("webp_size") val webpSize: String,
                @SerialName("width") val width: String
            )

            @Serializable
            data class FixedHeightSmall(
                @SerialName("height") val height: String,
                @SerialName("mp4") val mp4: String,
                @SerialName("mp4_size") val mp4Size: String,
                @SerialName("size") val size: String,
                @SerialName("url") val url: String,
                @SerialName("webp") val webp: String,
                @SerialName("webp_size") val webpSize: String,
                @SerialName("width") val width: String
            )

            @Serializable
            data class FixedHeightSmallStill(
                @SerialName("height") val height: String,
                @SerialName("size") val size: String,
                @SerialName("url") val url: String,
                @SerialName("width") val width: String
            )

            @Serializable
            data class FixedHeightStill(
                @SerialName("height") val height: String,
                @SerialName("size") val size: String,
                @SerialName("url") val url: String,
                @SerialName("width") val width: String
            )

            @Serializable
            data class FixedWidth(
                @SerialName("height") val height: String,
                @SerialName("mp4") val mp4: String,
                @SerialName("mp4_size") val mp4Size: String,
                @SerialName("size") val size: String,
                @SerialName("url") val url: String,
                @SerialName("webp") val webp: String,
                @SerialName("webp_size") val webpSize: String,
                @SerialName("width") val width: String
            )

            @Serializable
            data class FixedWidthDownsampled(
                @SerialName("height") val height: String,
                @SerialName("size") val size: String,
                @SerialName("url") val url: String,
                @SerialName("webp") val webp: String,
                @SerialName("webp_size") val webpSize: String,
                @SerialName("width") val width: String
            )

            @Serializable
            data class FixedWidthSmall(
                @SerialName("height") val height: String,
                @SerialName("mp4") val mp4: String,
                @SerialName("mp4_size") val mp4Size: String,
                @SerialName("size") val size: String,
                @SerialName("url") val url: String,
                @SerialName("webp") val webp: String,
                @SerialName("webp_size") val webpSize: String,
                @SerialName("width") val width: String
            )

            @Serializable
            data class FixedWidthSmallStill(
                @SerialName("height") val height: String,
                @SerialName("size") val size: String,
                @SerialName("url") val url: String,
                @SerialName("width") val width: String
            )

            @Serializable
            data class FixedWidthStill(
                @SerialName("height") val height: String,
                @SerialName("size") val size: String,
                @SerialName("url") val url: String,
                @SerialName("width") val width: String
            )

            @Serializable
            data class Looping(
                @SerialName("mp4") val mp4: String,
                @SerialName("mp4_size") val mp4Size: String
            )

            @Serializable
            data class Original(
                @SerialName("frames") val frames: String,
                @SerialName("hash") val hash: String,
                @SerialName("height") val height: String,
                @SerialName("mp4") val mp4: String,
                @SerialName("mp4_size") val mp4Size: String,
                @SerialName("size") val size: String,
                @SerialName("url") val url: String,
                @SerialName("webp") val webp: String,
                @SerialName("webp_size") val webpSize: String,
                @SerialName("width") val width: String
            )

            @Serializable
            data class OriginalMp4(
                @SerialName("height") val height: String,
                @SerialName("mp4") val mp4: String,
                @SerialName("mp4_size") val mp4Size: String,
                @SerialName("width") val width: String
            )

            @Serializable
            data class OriginalStill(
                @SerialName("height") val height: String,
                @SerialName("size") val size: String,
                @SerialName("url") val url: String,
                @SerialName("width") val width: String
            )

            @Serializable
            data class Preview(
                @SerialName("height") val height: String,
                @SerialName("mp4") val mp4: String,
                @SerialName("mp4_size") val mp4Size: String,
                @SerialName("width") val width: String
            )

            @Serializable
            data class PreviewGif(
                @SerialName("height") val height: String,
                @SerialName("size") val size: String,
                @SerialName("url") val url: String,
                @SerialName("width") val width: String
            )

            @Serializable
            data class PreviewWebp(
                @SerialName("height") val height: String,
                @SerialName("size") val size: String,
                @SerialName("url") val url: String,
                @SerialName("width") val width: String
            )

            @Serializable
            data class WStill(
                @SerialName("height") val height: String,
                @SerialName("url") val url: String,
                @SerialName("width") val width: String
            )
        }
    }

    @Serializable
    data class Meta(
        @SerialName("msg") val msg: String,
        @SerialName("response_id") val responseId: String,
        @SerialName("status") val status: Int
    )

    @Serializable
    data class Pagination(
        @SerialName("count") val count: Int,
        @SerialName("offset") val offset: Int,
        @SerialName("total_count") val totalCount: Int
    )
}