package co.lucaspinazzola.example.domain.interactor.gif

import co.lucaspinazzola.example.domain.repo.GiphyRepository


class UpdateGifsUseCase(private val giphyRepository: GiphyRepository) {

    suspend fun execute(query: String, offset: Int)  {
        giphyRepository.updateGifs(query, offset)
    }

}