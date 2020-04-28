package co.lucaspinazzola.example.data.db.helper

import com.squareup.sqldelight.Query
import kotlinx.coroutines.flow.Flow

interface DbHelper<D : Any> {

    fun getAll(): List<D>

    fun insert(items: List<D>)

    fun insert(c: D?)

    fun getById(id: String): D?

    fun deleteById(id: String)

    fun deleteAll()

    fun getChangeFlow(q: Query<D>) : Flow<List<D>>

    val count: Long

}
