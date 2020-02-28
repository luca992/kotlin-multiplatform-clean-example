package co.lucaspinazzola.example.data.db.helper

import co.lucaspinazzola.example.data.db.QueryPub
import com.squareup.sqldelight.Query

interface DbHelper<D : Any> {

    fun getAll(): List<D>

    fun insert(items: List<D>)

    fun insert(c: D?)

    fun getById(id: String): D?

    fun deleteById(id: String)

    fun deleteAll()

    fun getChangePublisher(q: Query<D>) : QueryPub<D, List<D>>

    val count: Long

}
