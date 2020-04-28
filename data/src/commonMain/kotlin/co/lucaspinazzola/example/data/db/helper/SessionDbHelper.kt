package co.lucaspinazzola.example.data.db.helper

import co.lucaspinazzola.example.data.model.SessionData
import co.lucaspinazzola.example.data.model.SessionDataQueries
import co.lucaspinazzola.example.data.model.sqldelight.Database


interface SessionDbHelper : DbHelper<SessionData>

class SessionDbHelperImpl(database: Database) : SqlDelightDbHelper<SessionData>(database), SessionDbHelper{


    override val queries: SessionDataQueries = database.sessionDataQueries


    override fun internalInsert(d: SessionData) {
        queries.insert(
                id = d.id,
                lastGiphySearchQuery = d.lastGiphySearchQuery)
    }

    override fun insert(items: List<SessionData>) {
        queries.transaction {
            for (s in items) {
                internalInsert(s)
            }
        }
    }

    override fun insert(c: SessionData?) {
        c?:return
        queries.transaction {
            internalInsert(c)
        }
    }

    override fun getById(id: String): SessionData? =
        queries.getById(id).executeAsOneOrNull()


    override fun getAll(): List<SessionData> =
        queries.getAll().executeAsList()


    override fun deleteById(id: String) {
        queries.deleteById(id)
    }

    override fun deleteAll(){
        queries.deleteAll()
    }

    override val count: Long
        get() = queries.count().executeAsOne()
}
