package co.lucaspinazzola.example.ui.giphy

import co.lucaspinazzola.example.InstantTaskExecutorRule
import co.lucaspinazzola.example.Rule
import co.lucaspinazzola.example.domain.interactor.gif.GetGifsAndListenForUpdatesUseCase
import co.lucaspinazzola.example.domain.interactor.gif.UpdateGifsUseCase
import co.lucaspinazzola.example.domain.interactor.session.GetLastGifQueryUseCase
import co.lucaspinazzola.example.domain.interactor.session.SetLastGifQueryUseCase
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
    private lateinit var viewModel: GiphyViewModel


    @MockK private lateinit var getGifsAndListenForUpdatesUseCase : GetGifsAndListenForUpdatesUseCase
    @MockK private lateinit var updateGifsUseCase: UpdateGifsUseCase
    @MockK private lateinit var setLastGifQueryUseCase: SetLastGifQueryUseCase
    @MockK private lateinit var getLastGifQueryUseCase: GetLastGifQueryUseCase



            @BeforeTest
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @Test
    fun `loadNextPage calls update gifs use case and stops refresh indicator on success`() = runTest {
        viewModel.loadingIndicatorVisibility.value = Visibility.VISIBLE
        coEvery{updateGifsUseCase.execute(any(), any())} just Runs

        viewModel.loadNextPage()
        viewModel.viewModelSupervisorJob!!.children.forEach {it.join()}

        coVerify(exactly = 1) { updateGifsUseCase.execute(any(), any()) }
        assertEquals(viewModel.loadingIndicatorVisibility.value, Visibility.GONE)
    }

    @Test
    fun `loadNextPage calls update gifs use case, throws error, and stops refresh indicator on failure`() = runTest {
        viewModel.loadingIndicatorVisibility.value = Visibility.VISIBLE
        coEvery{updateGifsUseCase.execute(any(), any())} throws Error("Test error")

        viewModel.loadNextPage()
        viewModel.viewModelSupervisorJob!!.children.forEach {it.join()}

        coVerify(exactly = 1) { updateGifsUseCase.execute(any(), any()) }
        assertEquals(viewModel.loadingIndicatorVisibility.value, Visibility.GONE)
    }

    @Test
    fun `getGifsAndListenForUpdatesUseCase, db and api produce, receives twice and stops loading indicator`() = runTest{
        viewModel.loadingIndicatorVisibility.value = Visibility.VISIBLE
        val query = ""
        val gifListsIn= listOf<List<Img>>(listOf(mockk{}),listOf(mockk{}, mockk{}))
        coEvery{getLastGifQueryUseCase.execute()} returns query
        val gifListsOut= mutableListOf<List<Img>>()
        every{getGifsAndListenForUpdatesUseCase.execute(query = any())} returns channelFlow {
            offer(gifListsIn[0])
            offer(gifListsIn[1])
        }
        var index = 0
        viewModel.gifs // triggers getConversationsAndListenForUpdatesJob
                .addObserver { value ->
                    if (index++ > 0) {
                        //ignore the first value. It is the initial value of the LiveData
                        gifListsOut.add(value)
                    }
                }
        viewModel.getGifsAndListenForUpdatesJob?.join()

        assertEquals (gifListsIn, gifListsOut)
        assertEquals(viewModel.loadingIndicatorVisibility.value, Visibility.GONE)
    }

    @Test
    fun `getGifsAndListenForUpdatesUseCase, db api only produces, receives error and stops loading indicator`() = runTest{
        viewModel.loadingIndicatorVisibility.value = Visibility.VISIBLE
        val query = ""
        val gifListsIn= listOf<List<Img>>(listOf(mockk{}))
        coEvery{getLastGifQueryUseCase.execute()} returns query
        val gifListsOut= mutableListOf<List<Img>>()
        every{getGifsAndListenForUpdatesUseCase.execute(query = any())} returns channelFlow {
            offer(gifListsIn[0])
            throw Error("Test Error")
        }
        var index = 0
        viewModel.gifs // triggers getConversationsAndListenForUpdatesJob
            .addObserver { value ->
                if (index++ > 0) {
                    //ignore the first value. It is the initial value of the LiveData
                    gifListsOut.add(value)
                }
            }
        viewModel.getGifsAndListenForUpdatesJob?.join()

        assertEquals (gifListsIn, gifListsOut)
        assertEquals(viewModel.loadingIndicatorVisibility.value, Visibility.GONE)
    }

}