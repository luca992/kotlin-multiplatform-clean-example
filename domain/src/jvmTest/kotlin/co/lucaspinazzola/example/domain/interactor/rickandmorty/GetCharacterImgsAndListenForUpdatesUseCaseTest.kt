import co.lucaspinazzola.example.domain.interactor.rickandmorty.GetCharacterImgsAndListenForUpdatesUseCase
import co.lucaspinazzola.example.domain.model.Img
import co.lucaspinazzola.example.domain.repo.RickAndMortyRepository
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
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetCharacterImgsAndListenForUpdatesUseCaseTest {

    @InjectMockKs
    private lateinit var useCase: GetCharacterImgsAndListenForUpdatesUseCase

    @MockK private lateinit var repository: RickAndMortyRepository

    private lateinit var parentJob : Job
    private val imgs : MutableList<Img> = mutableListOf()

    @BeforeTest
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)


        val broadcastChannel = ConflatedBroadcastChannel<List<Img>>()
        parentJob = Job()
        coEvery { repository.getCharacterImages() } returns imgs
        every { repository.listenForCharacterImageUpdates() } returns channelFlow {
            broadcastChannel.asFlow()
                    .collect {
                        this@channelFlow.offer(it)
                    }
        }
        coEvery { repository.updateCharacterImages(any()) } answers {
            broadcastChannel.offer(imgs)
        }
    }

    @Test
    fun `sends imgs from db, starts update listener, updates db from api, sends updated list`() = runTest {
        var count = 0

        GlobalScope.launch(parentJob) {
            useCase.execute().withIndex()
                    .onEach {
                        count = it.index+1
                        if (it.index+1==2) parentJob.cancel()
                    }.launchIn(this)
            delay(10000)
            throw Error("The updated images list was not received by the update listener after 10 sec")
        }

        parentJob.join()

        assertEquals(2,count)

        coVerifyOrder {
            repository.getCharacterImages()
            repository.listenForCharacterImageUpdates()
            repository.updateCharacterImages(any())
        }
    }




    @AfterTest
    fun afterTest(){
        imgs.clear()
    }



}