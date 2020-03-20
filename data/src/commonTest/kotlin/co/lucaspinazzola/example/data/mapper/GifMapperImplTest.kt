package co.lucaspinazzola.example.data.mapper


import co.lucaspinazzola.example.data.api.response.GiphySearchResponse
import co.lucaspinazzola.example.data.model.GifData
import co.lucaspinazzola.example.domain.model.Img
import co.lucaspinazzola.example.domain.utils.Date
import co.lucaspinazzola.example.runTest
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.mockk
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GifMapperImplTest {


    @InjectMockKs
    private lateinit var mapper: GifMapperImpl



    @BeforeTest
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @Test
    fun `data src correctly maps to domain output`() = runTest {
        val src = GifData.Impl(
            id = "1",
            resultIndex = 1,
            url = "url1",
            urlWebp = "urlWebp1",
            trendingDatetime = 1
        )
        val expected = Img(
            id = "1",
            resultIndex = 1,
            url = "url1",
            urlWebp = "urlWebp1",
            trendingDatetime = Date(1)
        )
        assertEquals(expected,mapper.toDomainModel(src))
    }

    @Test
    fun `data src array correctly maps to domain list output`() = runTest {
        val srcs = arrayOf<GifData>(
            GifData.Impl(
                id = "1",
                resultIndex = 1,
                url = "url1",
                urlWebp = "urlWebp1",
                trendingDatetime = 1
            ),
            GifData.Impl(
                id = "2",
                resultIndex = 2,
                url = "url2",
                urlWebp = "urlWebp2",
                trendingDatetime = 2
            )
        )
        val expected = listOf(
            Img(
                id = "1",
                resultIndex = 1,
                url = "url1",
                urlWebp = "urlWebp1",
                trendingDatetime = Date(1)
            ),
            Img(
                id = "2",
                resultIndex = 2,
                url = "url2",
                urlWebp = "urlWebp2",
                trendingDatetime = Date(2)
            )
        )
        assertEquals(expected,mapper.toDomainModel(srcs))
    }

    @Test
    fun `api src correctly maps to data output`() = runTest {
        val gifData1 : GiphySearchResponse.Data = mockk(relaxed = true) {}
        every { gifData1.id } returns "1"
        every { gifData1.images.original.url } returns "url1"
        every { gifData1.images.original.webp } returns "urlWebp1"
        every { gifData1.trendingDatetime } returns Date(1)
        val expected = GifData.Impl(
            id = "1",
            resultIndex = 1,
            url = "url1",
            urlWebp = "urlWebp1",
            trendingDatetime = 1
        )

        assertEquals(expected,mapper.toDataModel(gifData1,1))
    }

    @Test
    fun `api src array correctly maps to data list output`() = runTest {
        val gifData1 : GiphySearchResponse.Data = mockk(relaxed = true) {}
        every { gifData1.id } returns "1"
        every { gifData1.images.original.url } returns "url1"
        every { gifData1.images.original.webp } returns "urlWebp1"
        every { gifData1.trendingDatetime } returns Date(1)
        val gifData2 : GiphySearchResponse.Data = mockk(relaxed = true)  {}
        every { gifData2.id } returns "2"
        every { gifData2.images.original.url } returns "url2"
        every { gifData2.images.original.webp } returns "urlWebp2"
        every { gifData2.trendingDatetime } returns Date(2)
        val srcs = arrayOf(gifData1,gifData2)
        val expected = listOf<GifData>(
            GifData.Impl(
                id = "1",
                resultIndex = 1,
                url = "url1",
                urlWebp = "urlWebp1",
                trendingDatetime = 1
            ),
            GifData.Impl(
                id = "2",
                resultIndex = 2,
                url = "url2",
                urlWebp = "urlWebp2",
                trendingDatetime = 2
            )
        )
        assertEquals(expected,mapper.toDataModel(srcs, 1))
    }

}