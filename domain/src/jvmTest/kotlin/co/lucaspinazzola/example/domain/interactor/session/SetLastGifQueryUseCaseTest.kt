import co.lucaspinazzola.example.domain.interactor.session.GetLastGifQueryUseCase
import co.lucaspinazzola.example.domain.interactor.session.SetLastGifQueryUseCase
import co.lucaspinazzola.example.domain.model.Session
import co.lucaspinazzola.example.domain.repo.SessionRepository
import co.lucaspinazzola.example.runTest
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class SetLastGifQueryUseCaseTest {

    @InjectMockKs
    private lateinit var useCase: SetLastGifQueryUseCase

    @MockK
    private lateinit var repository: SessionRepository


    @BeforeTest
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @Test
    fun `when no session exists creates session with query as last query`() = runTest {
        val query = "doge"
        val session = null
        coEvery {
            repository.getSession()
        } returns session
        val slot = slot<Session>()
        coEvery{ repository.updateSession(capture(slot)) } returns Unit
        useCase.execute(query)
        coVerify(exactly = 1) {
            repository.getSession()
        }
        coVerify(exactly = 1) {
            repository.updateSession(any())
        }
        assertEquals(query, slot.captured.lastGiphySearchQuery)
    }

    @Test
    fun `when session exists updates session with query as last query`() = runTest {
        val query = "doge"
        val session = Session("1", "aaaaa")
        coEvery {
            repository.getSession()
        } returns session
        val slot = slot<Session>()
        coEvery{ repository.updateSession(capture(slot)) } returns Unit
        useCase.execute(query)
        coVerify(exactly = 1) {
            repository.getSession()
        }
        coVerify(exactly = 1) {
            repository.updateSession(any())
        }
        assertEquals(query, slot.captured.lastGiphySearchQuery)
    }
}