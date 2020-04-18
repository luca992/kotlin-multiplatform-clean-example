package co.lucaspinazzola.example.ui.rickandmorty

import co.lucaspinazzola.example.InstantTaskExecutorRule
import co.lucaspinazzola.example.Rule
import co.lucaspinazzola.example.domain.interactor.rickandmorty.GetCharacterImgsAndListenForUpdatesUseCase
import co.lucaspinazzola.example.domain.interactor.rickandmorty.UpdateCharacterImgsUseCase
import co.lucaspinazzola.example.domain.model.Img
import co.lucaspinazzola.example.runTest
import co.lucaspinazzola.example.ui.utils.Visibility
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.channelFlow
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GiphyViewModelTest() {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @InjectMockKs
    private lateinit var viewModel: RickAndMortyCharactersViewModel


    @MockK private lateinit var getCharacterImgsAndListenForUpdatesUseCase : GetCharacterImgsAndListenForUpdatesUseCase
    @MockK private lateinit var updateCharacterImgsUseCase: UpdateCharacterImgsUseCase



    @BeforeTest
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @Test
    fun `loadNextPage calls update character images use case and stops refresh indicator on success`() = runTest {
        viewModel.loadingIndicatorVisibility.value = Visibility.VISIBLE
        coEvery{updateCharacterImgsUseCase.execute(any())} just Runs

        viewModel.loadNextPage()
        viewModel.viewModelSupervisorJob!!.children.forEach {it.join()}

        coVerify(exactly = 1) { updateCharacterImgsUseCase.execute(any()) }
        assertEquals(viewModel.loadingIndicatorVisibility.value, Visibility.GONE)
    }

    @Test
    fun `loadNextPage calls update gifs use case, throws error, and stops refresh indicator on failure`() = runTest {
        viewModel.loadingIndicatorVisibility.value = Visibility.VISIBLE
        coEvery{updateCharacterImgsUseCase.execute(any())} throws Error("Test error")

        viewModel.loadNextPage()
        viewModel.viewModelSupervisorJob!!.children.forEach {it.join()}

        coVerify(exactly = 1) { updateCharacterImgsUseCase.execute(any()) }
        assertEquals(viewModel.loadingIndicatorVisibility.value, Visibility.GONE)
    }

    @Test
    fun `getCharacterImgsAndListenForUpdatesUseCase, db and api produce, receives twice and stops loading indicator`() = runTest{
        viewModel.loadingIndicatorVisibility.value = Visibility.VISIBLE
        val gifListsIn= listOf<List<Img>>(listOf(mockk{}),listOf(mockk{}, mockk{}))
        val gifListsOut= mutableListOf<List<Img>>()
        every{getCharacterImgsAndListenForUpdatesUseCase.execute()} returns channelFlow {
            offer(gifListsIn[0])
            offer(gifListsIn[1])
        }
        var index = 0
        viewModel.imgs // triggers getConversationsAndListenForUpdatesJob
                .addObserver { value ->
                    if (index++ > 0) {
                        //ignore the first value. It is the initial value of the LiveData
                        gifListsOut.add(value)
                    }
                }
        viewModel.getImgsAndListenForUpdatesJob?.join()

        assertEquals (gifListsIn, gifListsOut)
        assertEquals(viewModel.loadingIndicatorVisibility.value, Visibility.GONE)
    }

    @Test
    fun `getCharacterImgsAndListenForUpdatesUseCase, db api only produces, receives error and stops loading indicator`() = runTest{
        viewModel.loadingIndicatorVisibility.value = Visibility.VISIBLE
        val gifListsIn= listOf<List<Img>>(listOf(mockk{}))
        val gifListsOut= mutableListOf<List<Img>>()
        every{getCharacterImgsAndListenForUpdatesUseCase.execute()} returns channelFlow {
            offer(gifListsIn[0])
            throw Error("Test Error")
        }
        var index = 0
        viewModel.imgs // triggers getConversationsAndListenForUpdatesJob
            .addObserver { value ->
                if (index++ > 0) {
                    //ignore the first value. It is the initial value of the LiveData
                    gifListsOut.add(value)
                }
            }
        viewModel.getImgsAndListenForUpdatesJob?.join()

        assertEquals (gifListsIn, gifListsOut)
        assertEquals(viewModel.loadingIndicatorVisibility.value, Visibility.GONE)
    }

}