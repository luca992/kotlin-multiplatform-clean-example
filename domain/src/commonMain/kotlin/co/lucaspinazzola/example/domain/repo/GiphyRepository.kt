package co.lucaspinazzola.example.domain.repo

import co.lucaspinazzola.example.domain.model.Img
import kotlinx.coroutines.flow.Flow

interface GiphyRepository {

    suspend fun getGifs(): List<Img>
    suspend fun updateGifs(query: String, offset: Long)

    fun listenForGifUpdates(): Flow<List<Img>>

}