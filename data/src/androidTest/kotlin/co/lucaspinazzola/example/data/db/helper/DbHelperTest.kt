

import co.lucaspinazzola.example.data.db.helper.DbHelper
import co.lucaspinazzola.example.data.model.sqldelight.Database
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals


abstract class DbHelperTest<T: Any, D : DbHelper<T>> {


    var db: Database? = null

    protected open lateinit var dbHelper : D

    protected abstract val data1 : T
    protected abstract val data2 : T

    protected abstract val data1Id : String


    protected lateinit var dataList : MutableList<T>



    @BeforeTest
    open fun setUp() {
        createDriver()
        db = getDb()
        dataList = mutableListOf(
                data1,
                data2
        )
    }

    @AfterTest
    fun after(){
        closeDriver()
    }



    @Test
    fun `inserted equals original`() {
        dbHelper.insert(data1)
        val fromDb = dbHelper.getById(data1Id)
        assertEquals(data1,fromDb)
    }

    @Test
    fun `number inserted matches number in db`() {
        dbHelper.insert(dataList)
        val fromDb = dbHelper.getAll()
        assertEquals(dataList.size,fromDb.size)
    }


}

