package co.lucaspinazzola.example.data.db.helper

import co.lucaspinazzola.example.data.db.QueryPub
import co.lucaspinazzola.example.data.model.GifData
import co.lucaspinazzola.example.data.model.GifDataQueries
import co.lucaspinazzola.example.data.model.sqldelight.Database


interface GifDbHelper : DbHelper<GifData>{
    fun getAllChangePublisher(): QueryPub<GifData, List<GifData>>
}

class GifDbHelperImpl(database: Database) : SqlDelightDbHelper<GifData>(database), GifDbHelper{


    override val queries: GifDataQueries = database.gifDataQueries


    override fun internalInsert(d: GifData) {
        queries.insert(
            id = d.id,
            resultIndex = d.resultIndex,
            url = d.url,
            urlWebp = d.urlWebp,
            trendingDatetime = d.trendingDatetime)
    }


    override fun insert(items: List<GifData>) {
        queries.transaction {
            for (s in items) {
                internalInsert(s)
            }
        }
    }

    override fun insert(c: GifData?) {
        c?:return
        queries.transaction {
            internalInsert(c)
        }
    }

    override fun getById(id: String): GifData? =
        queries.getById(id).executeAsOneOrNull()

    override fun getAll(): List<GifData> =
        queries.getAll().executeAsList()

    override fun getAllChangePublisher(): QueryPub<GifData, List<GifData>> =
        getChangePublisher(queries.getAll())


    override fun deleteById(id: String) =
        queries.deleteById(id)

    override fun deleteAll(){
        queries.deleteAll()
    }

    override val count: Long
        get() = queries.count().executeAsOne()
}
