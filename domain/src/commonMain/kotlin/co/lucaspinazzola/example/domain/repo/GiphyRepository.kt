package co.lucaspinazzola.example.domain.repo

import co.lucaspinazzola.example.domain.model.Gif
import kotlinx.coroutines.flow.Flow

interface GiphyRepository {

    suspend fun getGifs(): List<Gif>
    suspend fun updateGifs(query: String, offset: Int)

    fun listenForGifUpdates(onChangePublisherSubscribed: suspend ()->Unit) : Flow<List<Gif>>

}