package co.lucaspinazzola.example.data.repo

import co.lucaspinazzola.example.data.db.helper.DbHelper
import co.lucaspinazzola.example.data.db.helper.SessionDbHelper
import co.lucaspinazzola.example.data.mapper.SessionMapper
import co.lucaspinazzola.example.data.model.SessionData
import co.lucaspinazzola.example.domain.model.Session
import co.lucaspinazzola.example.domain.repo.SessionRepository

data class SessionRepositoryImpl(private val sessionDbHelper: SessionDbHelper,
                                 private val sessionMapper: SessionMapper
) : SessionRepository {

    override suspend fun getSession(): Session? =
        sessionMapper.toDomainModel(sessionDbHelper.getAll().toTypedArray()).firstOrNull()

    override suspend fun updateSession(session: Session) =
        sessionDbHelper.insert(sessionMapper.toDataModel(session))

}
