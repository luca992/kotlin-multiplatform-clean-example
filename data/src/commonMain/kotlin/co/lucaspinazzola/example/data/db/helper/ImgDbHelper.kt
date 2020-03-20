package co.lucaspinazzola.example.data.db.helper

import co.lucaspinazzola.example.data.db.QueryPub
import co.lucaspinazzola.example.data.model.ImgData
import co.lucaspinazzola.example.data.model.ImgDataQueries
import co.lucaspinazzola.example.data.model.sqldelight.Database


interface ImgDbHelper : DbHelper<ImgData>{
    fun getAllChangePublisher(): QueryPub<ImgData, List<ImgData>>
}

class ImgDbHelperImpl(database: Database) : SqlDelightDbHelper<ImgData>(database), ImgDbHelper{


    override val queries: ImgDataQueries = database.imgDataQueries


    override fun internalInsert(d: ImgData) {
        queries.insert(
            id = d.id,
            resultIndex = d.resultIndex,
            url = d.url)
    }


    override fun insert(items: List<ImgData>) {
        queries.transaction {
            for (s in items) {
                internalInsert(s)
            }
        }
    }

    override fun insert(c: ImgData?) {
        c?:return
        queries.transaction {
            internalInsert(c)
        }
    }

    override fun getById(id: String): ImgData? =
        queries.getById(id).executeAsOneOrNull()

    override fun getAll(): List<ImgData> =
        queries.getAll().executeAsList()

    override fun getAllChangePublisher(): QueryPub<ImgData, List<ImgData>> =
        getChangePublisher(queries.getAll())


    override fun deleteById(id: String) =
        queries.deleteById(id)

    override fun deleteAll(){
        queries.deleteAll()
    }

    override val count: Long
        get() = queries.count().executeAsOne()
}
