package co.lucaspinazzola.example.domain.interactor.session

import co.lucaspinazzola.example.domain.repo.SessionRepository


class GetLastGifQueryUseCase(private val sessionRepository: SessionRepository) {

    suspend fun execute() : String? =
        sessionRepository.getSession()?.lastGiphySearchQuery

}