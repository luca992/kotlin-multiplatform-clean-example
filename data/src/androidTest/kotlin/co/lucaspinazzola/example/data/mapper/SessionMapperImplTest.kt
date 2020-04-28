package co.lucaspinazzola.example.data.mapper


import co.lucaspinazzola.example.data.model.SessionData
import co.lucaspinazzola.example.domain.model.Session
import co.lucaspinazzola.example.runTest
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class SessionMapperImplTest {


    @InjectMockKs
    private lateinit var mapper: SessionMapperImpl



    @BeforeTest
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @Test
    fun `data src correctly maps to domain output`() = runTest {
        val src = SessionData.Impl(
            id = "1",
            lastGiphySearchQuery = "query"
        )
        val expected = Session(
            id = "1",
            lastGiphySearchQuery = "query"
        )
        assertEquals(expected,mapper.toDomainModel(src))
    }

    @Test
    fun `data src list correctly maps to domain list output`() = runTest {
        val srcs = arrayOf<SessionData>(
            SessionData.Impl(
                id = "1",
                lastGiphySearchQuery = "query"
            ),
            SessionData.Impl(
                id = "2",
                lastGiphySearchQuery = "query2"
            )
        )
        val expected = listOf(
            Session(
                id = "1",
                lastGiphySearchQuery = "query"
            ),
            Session(
                id = "2",
                lastGiphySearchQuery = "query2"
            )
        )
        assertEquals(expected,mapper.toDomainModel(srcs))
    }

    @Test
    fun `domain src correctly maps to data output`() = runTest {
        val src = Session(
            id = "1",
            lastGiphySearchQuery = "query"
        )
        val expected = SessionData.Impl(
            id = "1",
            lastGiphySearchQuery = "query"
        )
        assertEquals(expected,mapper.toDataModel(src))
    }

}