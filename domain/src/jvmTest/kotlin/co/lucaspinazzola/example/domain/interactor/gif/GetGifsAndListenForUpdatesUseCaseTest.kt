import co.lucaspinazzola.example.domain.interactor.gif.GetGifsAndListenForUpdatesUseCase
import co.lucaspinazzola.example.domain.model.Img
import co.lucaspinazzola.example.domain.repo.GiphyRepository
import co.lucaspinazzola.example.runTest
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.test.*

class GetGifsAndListenForUpdatesUseCaseTest {

    @InjectMockKs
    private lateinit var useCase: GetGifsAndListenForUpdatesUseCase

    @MockK private lateinit var repository: GiphyRepository

    private lateinit var parentJob : Job
    private val query = "query"
    private val imgs : MutableList<Img> = mutableListOf()

    @BeforeTest
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)


        val broadcastChannel = ConflatedBroadcastChannel<List<Img>>()
        parentJob = Job()
        coEvery { repository.getGifs() } returns imgs
        every { repository.listenForGifUpdates() } returns channelFlow {
            broadcastChannel.asFlow()
                    .collect {
                        this@channelFlow.offer(it)
                    }
        }
        coEvery { repository.updateGifs(query, any()) } answers {
            broadcastChannel.offer(imgs)
        }
    }

    @Test
    fun `sends gifs from db, starts update listener, updates db from api, sends updated list`() = runTest {
        var count = 0

        GlobalScope.launch(parentJob) {
            useCase.execute(query).withIndex()
                    .onEach {
                        count = it.index+1
                        if (it.index+1==2) parentJob.cancel()
                    }.launchIn(this)
            delay(10000)
            throw Error("The updated gifs list was not received by the update listener after 10 sec")
        }

        parentJob.join()

        assertEquals(2,count)

        coVerifyOrder {
            repository.getGifs()
            repository.listenForGifUpdates()
            repository.updateGifs(query, any())
        }
    }




    @AfterTest
    fun afterTest(){
        imgs.clear()
    }



}