
import co.lucaspinazzola.example.data.db.helper.SessionDbHelper
import co.lucaspinazzola.example.data.db.helper.SessionDbHelperImpl
import co.lucaspinazzola.example.data.model.SessionData
import kotlin.test.BeforeTest


class SessionDbHelperImplTest: DbHelperTest<SessionData, SessionDbHelper>() {


    override lateinit var dbHelper : SessionDbHelper

    override val data1 = SessionData.Impl(
        id = "1",
        lastGiphySearchQuery = "query"
    )

    override val data2 = SessionData.Impl(
        id = "2",
        lastGiphySearchQuery = null
    )

    override val data1Id: String = data1.id



    @BeforeTest
    override fun setUp() {
        super.setUp()
        dbHelper = SessionDbHelperImpl(db!!)
    }


}

