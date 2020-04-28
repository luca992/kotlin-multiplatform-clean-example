package co.lucaspinazzola.example.data.mapper

import co.lucaspinazzola.example.data.model.SessionData
import co.lucaspinazzola.example.domain.model.Session

class SessionMapperImpl : SessionMapper {


    override fun toDomainModel(src: Array<SessionData>): List<Session> =
        src.map{ toDomainModel(it) }

    override fun toDomainModel(src: SessionData): Session =
            Session(
                id = src.id,
                lastGiphySearchQuery = src.lastGiphySearchQuery)

    override fun toDataModel(src: Session) = SessionData.Impl(
        id = src.id,
        lastGiphySearchQuery = src.lastGiphySearchQuery
    )
}