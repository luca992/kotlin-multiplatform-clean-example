package co.lucaspinazzola.example.data.repo

import co.lucaspinazzola.example.data.api.giphy.GiphyApi
import co.lucaspinazzola.example.data.api.giphy.response.GiphySearchResponse
import co.lucaspinazzola.example.data.db.QueryPub
import co.lucaspinazzola.example.data.db.helper.GifDbHelper
import co.lucaspinazzola.example.data.mapper.ImgMapper
import co.lucaspinazzola.example.data.model.ImgData
import co.lucaspinazzola.example.domain.model.Img
import co.lucaspinazzola.example.runTest
import com.squareup.sqldelight.Query
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlin.test.*

class GiphyRepositoryImplTest {


    @InjectMockKs
    private lateinit var repository: GiphyRepositoryImpl

    @MockK lateinit var api: GiphyApi
    @MockK lateinit var gifDbHelper: GifDbHelper
    @MockK lateinit var imgMapper: ImgMapper


    @MockK(relaxed = true)
    private lateinit var gifsQuery : Query<ImgData>


    @BeforeTest
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)

    }


    @Test
    fun `getGifs gets all gifs from db`() = runTest {
        val dataList = emptyList<ImgData>()
        val domainList = listOf<Img>()
        every { gifDbHelper.getAll() } returns dataList
        every { imgMapper.toDomainModel(dataList.toTypedArray()) } returns domainList
        val result = repository.getGifs()
        verify (exactly = 1) {
            gifDbHelper.getAll()
        }
        assertSame(domainList, result)
    }

    @Test
    fun `updateGifs offset 0, clears db, gets 1st page of gifs from api, updates db`() = runTest {
        val query = "query"
        val offset = 0L
        val response : GiphySearchResponse = mockk{}
        val responseData : List<GiphySearchResponse.Data> = listOf()
        val dataGifs : List<ImgData> = listOf()
        every { response.data } returns responseData
        coEvery { api.searchGifs(query, offset) } returns response
        every { imgMapper.toDataModel(responseData.toTypedArray(),offset) } returns dataGifs

        repository.updateGifs(query, offset)

        verify (exactly = 1)  { gifDbHelper.deleteAll() }
        coVerify (exactly = 1)  { api.searchGifs(query, offset) }

        verify (exactly = 1)  { gifDbHelper.insert(any<List<ImgData>>()) }
    }

    @Test
    fun `updateGifs offset greater than 0 doesn't clear db, gets 1st page of gifs from api, updates db`() = runTest {
        val query = "query"
        val offset = 1L
        val response : GiphySearchResponse = mockk{}
        val responseData : List<GiphySearchResponse.Data> = listOf()
        val dataGifs : List<ImgData> = listOf()
        every { response.data } returns responseData
        coEvery { api.searchGifs(query, offset) } returns response
        every { imgMapper.toDataModel(responseData.toTypedArray(),offset) } returns dataGifs

        repository.updateGifs(query, offset)

        verify (exactly = 0)  { gifDbHelper.deleteAll() }
        coVerify (exactly = 1)  { api.searchGifs(query, offset) }

        verify (exactly = 1)  { gifDbHelper.insert(any<List<ImgData>>()) }
    }

    @Test
    fun `listenForGifUpdates produces on db change`()= runTest {
        val dataList = emptyArray<ImgData>()
        val pub = QueryPub(gifsQuery) {it.executeAsList()}
        val domainList = emptyList<Img>()
        every { imgMapper.toDomainModel(dataList) } returns domainList
        every { gifDbHelper.getAllChangePublisher() } returns pub
        val parentJob = Job()
        var count = 0
        GlobalScope.launch(parentJob) {
            repository.listenForGifUpdates(){
                pub.applyNext { dataList.toList() }
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