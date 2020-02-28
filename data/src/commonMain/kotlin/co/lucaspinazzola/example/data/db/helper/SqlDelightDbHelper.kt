package co.lucaspinazzola.example.data.db.helper

import co.lucaspinazzola.example.data.db.QueryPub
import co.lucaspinazzola.example.data.model.sqldelight.Database
import com.squareup.sqldelight.Query
import com.squareup.sqldelight.Transacter

abstract class SqlDelightDbHelper<D : Any>(val database: Database) : DbHelper<D>{
    abstract val queries: Transacter

    protected abstract fun internalInsert(d: D)

    override fun getChangePublisher(q: Query<D>) : QueryPub<D, List<D>> {
        return QueryPub(q) {it.executeAsList()}
    }
}