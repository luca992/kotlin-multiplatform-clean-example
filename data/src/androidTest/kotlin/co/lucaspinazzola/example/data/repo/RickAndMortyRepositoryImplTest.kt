package co.lucaspinazzola.example.data.repo

import co.lucaspinazzola.example.data.api.rickandmorty.RickAndMortyApi
import co.lucaspinazzola.example.data.api.rickandmorty.response.RickAndMortySearchResponse
import co.lucaspinazzola.example.data.db.helper.ImgDbHelper
import co.lucaspinazzola.example.data.mapper.ImgMapper
import co.lucaspinazzola.example.data.model.ImgData
import co.lucaspinazzola.example.domain.model.Img
import co.lucaspinazzola.example.runTest
import com.squareup.sqldelight.Query
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertSame

class RickAndMortyRepositoryImplTest {


    @InjectMockKs
    private lateinit var repository: RickAndMortyRepositoryImpl

    @MockK lateinit var api: RickAndMortyApi
    @MockK lateinit var imgDbHelper: ImgDbHelper
    @MockK lateinit var imgMapper: ImgMapper


    @MockK(relaxed = true)
    private lateinit var gifsQuery : Query<ImgData>


    @BeforeTest
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)

    }


    @Test
    fun `getCharacterImages gets all imgs from db`() = runTest {
        val dataList = emptyList<ImgData>()
        val domainList = listOf<Img>()
        every { imgDbHelper.getAll() } returns dataList
        every { imgMapper.toDomainModel(dataList.toTypedArray()) } returns domainList
        val result = repository.getCharacterImages()
        verify (exactly = 1) {
            imgDbHelper.getAll()
        }
        assertSame(domainList, result)
    }

    @Test
    fun `updateGifs page 1 gets 1st page of gifs from api, updates db`() = runTest {
        val page = 1L
        val response : RickAndMortySearchResponse = mockk{}
        val responseData : List<RickAndMortySearchResponse.Result> = listOf()
        val dataGifs : List<ImgData> = listOf()
        every { response.results } returns responseData
        coEvery { api.getCharacters(page) } returns response
        every { imgMapper.toDataModel(responseData.toTypedArray()) } returns dataGifs

        repository.updateCharacterImages(page)

        coVerify (exactly = 1)  { api.getCharacters(page) }

        verify (exactly = 1)  { imgDbHelper.insert(any<List<ImgData>>()) }
    }


    @Test
    fun `listenForGifUpdates produces on db change`()= runTest {
        val dataList = emptyArray<ImgData>()
        val pub = gifsQuery.asFlow().mapToList()
        val domainList = emptyList<Img>()
        every { imgMapper.toDomainModel(dataList) } returns domainList
        every { imgDbHelper.getAllChangePublisher() } returns pub
        val parentJob = Job()
        var count = 0
        GlobalScope.launch(parentJob) {
            repository.listenForCharacterImageUpdates()
                    .onStart {
                        emit(domainList)
                    }.onEach {
                        count++
                        //did produce on db change
                        parentJob.cancel()
                    }.launchIn(this)
        }

        GlobalScope.launch(parentJob) {
            delay(10000)
            //didn't produce... close to finish test
            parentJob.cancel()
        }
        parentJob.join()

        assertEquals(1,count)
    }


}