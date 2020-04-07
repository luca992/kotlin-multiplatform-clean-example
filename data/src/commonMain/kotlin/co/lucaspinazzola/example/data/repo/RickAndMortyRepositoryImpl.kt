package co.lucaspinazzola.example.data.repo
import co.lucaspinazzola.example.data.api.rickandmorty.RickAndMortyApi
import co.lucaspinazzola.example.data.db.helper.ImgDbHelper
import co.lucaspinazzola.example.data.mapper.ImgMapper
import co.lucaspinazzola.example.domain.model.Img
import co.lucaspinazzola.example.domain.repo.RickAndMortyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

data class RickAndMortyRepositoryImpl(private val api: RickAndMortyApi,
                                      private val imgDbHelper: ImgDbHelper,
                                      private val imgMapper: ImgMapper) : RickAndMortyRepository {


    override suspend fun getCharacterImages(): List<Img> {
        return imgMapper.toDomainModel(imgDbHelper.getAll().toTypedArray())
    }

    override suspend fun updateCharacterImages(page: Long) {
        val responseData = api.getCharacters(page).results.toTypedArray()
        imgDbHelper.insert(imgMapper.toDataModel(responseData))
    }


    override fun listenForCharacterImageUpdates(): Flow<List<Img>> =
            imgDbHelper.getAllChangePublisher()
                    .map { imgMapper.toDomainModel(it.toTypedArray()) }


}
