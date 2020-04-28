package co.lucaspinazzola.example.domain.interactor.session

import co.lucaspinazzola.example.domain.model.Session
import co.lucaspinazzola.example.domain.repo.SessionRepository


class SetLastGifQueryUseCase(private val sessionRepository: SessionRepository) {

    suspend fun execute(query: String) {
        val session = sessionRepository.getSession()
        if (session != null) {
            sessionRepository.updateSession(session.copy(lastGiphySearchQuery = query))
        } else {
            sessionRepository.updateSession(Session(
                id = "1",
                lastGiphySearchQuery = query)
            )
        }
    }

}