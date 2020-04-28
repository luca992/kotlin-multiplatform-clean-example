import co.lucaspinazzola.example.domain.interactor.gif.UpdateGifsUseCase
import co.lucaspinazzola.example.domain.repo.GiphyRepository
import co.lucaspinazzola.example.runTest
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlin.test.BeforeTest
import kotlin.test.Test

class UpdateGifsUseCaseTest {

    @InjectMockKs
    private lateinit var updateConversationsUseCase: UpdateGifsUseCase

    @MockK private lateinit var repository: GiphyRepository


    @BeforeTest
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @Test
    fun `calls update conversations repo function`() = runTest {
        val query = "query"
        val offset = 0L
        updateConversationsUseCase.execute(query,offset)

        coVerify(exactly = 1) { repository.updateGifs(query,offset) }
    }

}