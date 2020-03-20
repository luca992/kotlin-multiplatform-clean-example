package co.lucaspinazzola.example.data.repo
import co.lucaspinazzola.example.data.api.giphy.GiphyApi
import co.lucaspinazzola.example.data.db.architecture.Sub
import co.lucaspinazzola.example.data.db.helper.GifDbHelper
import co.lucaspinazzola.example.data.mapper.ImgMapper
import co.lucaspinazzola.example.data.model.ImgData
import co.lucaspinazzola.example.domain.model.Img
import co.lucaspinazzola.example.domain.repo.GiphyRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

data class GiphyRepositoryImpl(private val api: GiphyApi,
                               private val gifDbHelper: GifDbHelper,
                               private val imgMapper: ImgMapper) : GiphyRepository {


    override suspend fun getGifs(): List<Img> {
        return imgMapper.toDomainModel(gifDbHelper.getAll().toTypedArray())
    }

    override suspend fun updateGifs(query: String, offset: Long) {
        if (offset == 0L) gifDbHelper.deleteAll()
        val responseData = api.searchGifs(query, offset).data.toTypedArray()
        gifDbHelper.insert(imgMapper.toDataModel(responseData, offset))
    }


    override fun listenForGifUpdates(
        onChangePublisherSubscribed: suspend () -> Unit
    ): Flow<List<Img>> =
            callbackFlow {
                val pub = gifDbHelper.getAllChangePublisher()
                pub.addSub(object : Sub<List<ImgData>> {
                    override fun onNext(next: List<ImgData>) {
                        val items = imgMapper.toDomainModel(next.toTypedArray())
                        if (!isClosedForSend) {
                            offer(items)
                        }
                    }
                    override fun onError(t: Throwable) {
                        throw t
                    }
                })
                onChangePublisherSubscribed()
                awaitClose{
                    pub.destroy()
                }
            }


}
