package co.lucaspinazzola.example.data.mapper


import co.lucaspinazzola.example.data.api.giphy.response.GiphySearchResponse
import co.lucaspinazzola.example.data.api.rickandmorty.response.RickAndMortySearchResponse
import co.lucaspinazzola.example.data.model.ImgData
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
    private lateinit var mapper: ImgMapperImpl



    @BeforeTest
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @Test
    fun `data src correctly maps to domain output`() = runTest {
        val src = ImgData.Impl(
            id = "1",
            resultIndex = 1,
            url = "url1"
        )
        val expected = Img(
            id = "1",
            resultIndex = 1,
            url = "url1"
        )
        assertEquals(expected,mapper.toDomainModel(src))
    }

    @Test
    fun `data src array correctly maps to domain list output`() = runTest {
        val srcs = arrayOf<ImgData>(
            ImgData.Impl(
                id = "1",
                resultIndex = 1,
                url = "url1"
            ),
            ImgData.Impl(
                id = "2",
                resultIndex = 2,
                url = "url2"
            )
        )
        val expected = listOf(
            Img(
                id = "1",
                resultIndex = 1,
                url = "url1"
            ),
            Img(
                id = "2",
                resultIndex = 2,
                url = "url2"
            )
        )
        assertEquals(expected,mapper.toDomainModel(srcs))
    }

    @Test
    fun `api src correctly maps to data output`() = runTest {
        val imgData1 : GiphySearchResponse.Data = mockk(relaxed = true) {}
        every { imgData1.id } returns "1"
        every { imgData1.images.original.webp } returns "url1"
        every { imgData1.trendingDatetime } returns Date(1)
        val expected = ImgData.Impl(
            id = "1",
            resultIndex = 1,
            url = "url1"
        )

        assertEquals(expected,mapper.toDataModel(imgData1,1))
    }

    @Test
    fun `api src array correctly maps to data list output`() = runTest {
        val imgData1 : GiphySearchResponse.Data = mockk(relaxed = true) {}
        every { imgData1.id } returns "1"
        every { imgData1.images.original.webp } returns "url1"
        every { imgData1.trendingDatetime } returns Date(1)
        val imgData2 : GiphySearchResponse.Data = mockk(relaxed = true)  {}
        every { imgData2.id } returns "2"
        every { imgData2.images.original.webp } returns "url2"
        every { imgData2.trendingDatetime } returns Date(2)
        val srcs = arrayOf(imgData1,imgData2)
        val expected = listOf<ImgData>(
            ImgData.Impl(
                id = "1",
                resultIndex = 1,
                url = "url1"
            ),
            ImgData.Impl(
                id = "2",
                resultIndex = 2,
                url = "url2"
            )
        )
        assertEquals(expected,mapper.toDataModel(srcs, 1))
    }


    @Test
    fun `rick and morty api src correctly maps to data output`() = runTest {
        val imgData1 : RickAndMortySearchResponse.Result = mockk(relaxed = true) {}
        every { imgData1.id } returns "1"
        every { imgData1.image } returns "url1"
        val expected = ImgData.Impl(
            id = "1",
            resultIndex = 1,
            url = "url1"
        )

        assertEquals(expected,mapper.toDataModel(imgData1))
    }

    @Test
    fun `rick and morty api src array correctly maps to data list output`() = runTest {
        val imgData1 : RickAndMortySearchResponse.Result = mockk(relaxed = true) {}
        every { imgData1.id } returns "1"
        every { imgData1.image } returns "url1"
        val imgData2 : RickAndMortySearchResponse.Result = mockk(relaxed = true) {}
        every { imgData2.id } returns "2"
        every { imgData2.image } returns "url2"
        val srcs = arrayOf(imgData1,imgData2)
        val expected = listOf<ImgData>(
            ImgData.Impl(
                id = "1",
                resultIndex = 1,
                url = "url1"
            ),
            ImgData.Impl(
                id = "2",
                resultIndex = 2,
                url = "url2"
            )
        )
        assertEquals(expected,mapper.toDataModel(srcs))
    }


}