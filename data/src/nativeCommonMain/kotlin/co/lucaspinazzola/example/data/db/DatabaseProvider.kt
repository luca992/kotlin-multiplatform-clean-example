package co.lucaspinazzola.example.data.db

import co.lucaspinazzola.example.data.model.sqldelight.Database
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

actual fun initSqldelightDatabase(): SqlDriver {
    return NativeSqliteDriver(Database.Schema, "snackchat.db")
}