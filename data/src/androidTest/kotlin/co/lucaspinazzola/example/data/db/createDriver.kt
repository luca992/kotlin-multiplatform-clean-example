import co.lucaspinazzola.example.data.model.sqldelight.Database
import co.lucaspinazzola.example.data.model.sqldelight.Database.Companion.Schema
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver.Companion.IN_MEMORY

/*actual */fun createDriver() {
    val driver = JdbcSqliteDriver(IN_MEMORY)
    Schema.create(driver)
    Db.dbSetup(driver)
}


/*actual */fun closeDriver() {
    Db.dbClear()
}

/*actual */fun getDb(): Database = Db.instance