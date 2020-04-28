import co.lucaspinazzola.example.domain.interactor.session.GetLastGifQueryUseCase
import co.lucaspinazzola.example.domain.model.Session
import co.lucaspinazzola.example.domain.repo.SessionRepository
import co.lucaspinazzola.example.runTest
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetLastGifQueryUseCaseTest {

    @InjectMockKs
    private lateinit var useCase: GetLastGifQueryUseCase

    @MockK
    private lateinit var repository: SessionRepository

    @MockK lateinit var session: Session

    @BeforeTest
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @Test
    fun `gets last query from session`() = runTest {
        val lastQuery = "doge"
        every { session.lastGiphySearchQuery } returns "doge"
        coEvery {
            repository.getSession()
        } returns session

        val result =  useCase.execute()
        coVerify(exactly = 1) {
            repository.getSession()
        }
        assertEquals(lastQuery, result)
    }

}