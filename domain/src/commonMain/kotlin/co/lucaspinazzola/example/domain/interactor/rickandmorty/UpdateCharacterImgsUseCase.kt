package co.lucaspinazzola.example.domain.interactor.rickandmorty

import co.lucaspinazzola.example.domain.repo.RickAndMortyRepository


class UpdateCharacterImgsUseCase(private val rickAndMortyRepository: RickAndMortyRepository) {

    suspend fun execute(page: Long)  {
        rickAndMortyRepository.updateCharacterImages(page)
    }

}