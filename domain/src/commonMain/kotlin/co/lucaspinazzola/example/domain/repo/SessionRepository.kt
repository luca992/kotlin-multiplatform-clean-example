package co.lucaspinazzola.example.domain.repo

import co.lucaspinazzola.example.domain.model.Session

interface SessionRepository {

    suspend fun getSession(): Session?

    suspend fun updateSession(session: Session): Any
}