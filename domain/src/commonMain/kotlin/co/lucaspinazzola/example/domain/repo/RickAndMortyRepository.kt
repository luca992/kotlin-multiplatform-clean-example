package co.lucaspinazzola.example.domain.repo

import co.lucaspinazzola.example.domain.model.Img
import kotlinx.coroutines.flow.Flow

interface RickAndMortyRepository {

    suspend fun getCharacterImages(): List<Img>
    suspend fun updateCharacterImages(page: Long)

    fun listenForCharacterImageUpdates(): Flow<List<Img>>

}