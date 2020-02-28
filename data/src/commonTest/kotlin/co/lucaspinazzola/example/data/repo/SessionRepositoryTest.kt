package co.lucaspinazzola.example.data.repo

import co.lucaspinazzola.example.data.api.GiphyApi
import co.lucaspinazzola.example.data.api.response.GiphySearchResponse
import co.lucaspinazzola.example.data.db.helper.DbHelper
import co.lucaspinazzola.example.data.mapper.SessionMapper
import co.lucaspinazzola.example.data.model.SessionData
import co.lucaspinazzola.example.domain.model.Session
import co.lucaspinazzola.example.runTest
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class SessionRepositoryTest {


    @InjectMockKs
    private lateinit var repository: SessionRepositoryImpl

    @MockK lateinit var api: GiphyApi
    @MockK lateinit var sessionMapper: SessionMapper
    @MockK lateinit var session: Session
    @MockK lateinit var response : GiphySearchResponse
    @MockK lateinit var sessionDbHelper : DbHelper<SessionData>



    @BeforeTest
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @Test
    fun `getSession returns session from Db if exists`() = runTest {
        val sessionDatas = listOf<SessionData>(mockk{})
        val sessions = listOf<Session>(mockk{})

        every { sessionDbHelper.getAll() } returns sessionDatas
        every { sessionMapper.toDomainModel(sessionDatas.toTypedArray()) } returns sessions
        val session = repository.getSession()
        assertNotNull(session)
    }

    @Test
    fun `getSession returns session from Db if doesn't exist`() = runTest {
        val sessionDatas = listOf<SessionData>()
        val sessions = listOf<Session>()

        every { sessionDbHelper.getAll() } returns sessionDatas
        every { sessionMapper.toDomainModel(sessionDatas.toTypedArray()) } returns sessions

        val session = repository.getSession()
        assertNull(session)
    }


}