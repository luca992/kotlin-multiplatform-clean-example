import co.lucaspinazzola.example.domain.interactor.gif.UpdateGifsUseCase
import co.lucaspinazzola.example.domain.interactor.rickandmorty.UpdateCharacterImgsUseCase
import co.lucaspinazzola.example.domain.repo.GiphyRepository
import co.lucaspinazzola.example.domain.repo.RickAndMortyRepository
import co.lucaspinazzola.example.runTest
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlin.test.BeforeTest
import kotlin.test.Test

class UpdateCharacterImgsUseCaseTest {

    @InjectMockKs
    private lateinit var useCase: UpdateCharacterImgsUseCase

    @MockK private lateinit var repository: RickAndMortyRepository


    @BeforeTest
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @Test
    fun `calls update character images repo function`() = runTest {
        val page = 0L
        useCase.execute(page)

        coVerify(exactly = 1) { repository.updateCharacterImages(page) }
    }

}