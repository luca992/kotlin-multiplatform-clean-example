package co.lucaspinazzola.example.data.db


import co.lucaspinazzola.example.data.model.sqldelight.Database
import co.lucaspinazzola.example.data.utils.application
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

actual fun initSqldelightDatabase(): SqlDriver {
    return AndroidSqliteDriver(Database.Schema, application, "snackchat.db")
}