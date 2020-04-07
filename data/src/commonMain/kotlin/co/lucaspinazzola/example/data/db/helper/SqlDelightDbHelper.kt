package co.lucaspinazzola.example.data.db.helper

import co.lucaspinazzola.example.data.model.sqldelight.Database
import com.squareup.sqldelight.Query
import com.squareup.sqldelight.Transacter
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.Flow

abstract class SqlDelightDbHelper<D : Any>(val database: Database) : DbHelper<D>{
    abstract val queries: Transacter

    protected abstract fun internalInsert(d: D)

    override fun getChangeFlow(q: Query<D>) : Flow<List<D>> {
        return q.asFlow().mapToList()
    }
}