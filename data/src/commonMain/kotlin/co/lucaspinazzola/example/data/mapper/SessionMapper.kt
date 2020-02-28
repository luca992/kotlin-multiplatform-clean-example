package co.lucaspinazzola.example.data.mapper

import co.lucaspinazzola.example.data.model.SessionData
import co.lucaspinazzola.example.domain.model.Session

interface SessionMapper {

    fun toDomainModel(src: Array<SessionData>): List<Session>
    fun toDomainModel(src: SessionData): Session
    fun toDataModel(src: Session): SessionData

}