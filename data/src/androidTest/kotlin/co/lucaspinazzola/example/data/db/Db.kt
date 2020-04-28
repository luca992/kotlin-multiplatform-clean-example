
import co.lucaspinazzola.example.data.model.sqldelight.Database
import co.touchlab.stately.concurrency.AtomicReference
import co.touchlab.stately.concurrency.value
import co.touchlab.stately.freeze
import com.squareup.sqldelight.db.SqlDriver

object Db {
  private val driverRef = AtomicReference<SqlDriver?>(null)
  private val dbRef = AtomicReference<Database?>(null)

  fun dbSetup(driver: SqlDriver) {
    val db = Database(driver)
    driverRef.value = driver.freeze()
    dbRef.value = db.freeze()
  }

  internal fun dbClear() {
    driverRef.value!!.close()
    dbRef.value = null
    driverRef.value = null
  }

  val instance: Database
    get() = dbRef.value!!
}