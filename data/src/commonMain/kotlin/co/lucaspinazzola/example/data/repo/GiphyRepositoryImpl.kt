package co.lucaspinazzola.example.data.repo
import co.lucaspinazzola.example.data.api.GiphyApi
import co.lucaspinazzola.example.data.db.architecture.Sub
import co.lucaspinazzola.example.data.db.helper.GifDbHelper
import co.lucaspinazzola.example.data.mapper.GifMapper
import co.lucaspinazzola.example.data.model.GifData
import co.lucaspinazzola.example.domain.model.Gif
import co.lucaspinazzola.example.domain.repo.GiphyRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

data class GiphyRepositoryImpl(private val api: GiphyApi,
                               private val gifDbHelper: GifDbHelper,
                               private val gifMapper: GifMapper) : GiphyRepository {


    override suspend fun getGifs(): List<Gif> {
        return gifMapper.toDomainModel(gifDbHelper.getAll().toTypedArray())
    }

    override suspend fun updateGifs(query: String, offset: Int) {
        if (offset == 0) gifDbHelper.deleteAll()
        val responseData = api.searchGifs(query, offset).data.toTypedArray()
        gifDbHelper.insert(gifMapper.toDataModel(responseData))
    }


    override fun listenForGifUpdates(
        onChangePublisherSubscribed: suspend () -> Unit
    ): Flow<List<Gif>> =
            callbackFlow {
                val pub = gifDbHelper.getAllChangePublisher()
                pub.addSub(object : Sub<List<GifData>> {
                    override fun onNext(next: List<GifData>) {
                        val items = gifMapper.toDomainModel(next.toTypedArray())
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