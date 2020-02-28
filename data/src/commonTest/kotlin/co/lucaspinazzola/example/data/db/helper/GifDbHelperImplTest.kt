
import co.lucaspinazzola.example.data.db.helper.GifDbHelper
import co.lucaspinazzola.example.data.db.helper.GifDbHelperImpl
import co.lucaspinazzola.example.data.model.GifData
import kotlin.test.BeforeTest


class GifDbHelperImplTest: DbHelperTest<GifData, GifDbHelper>() {


    override lateinit var dbHelper : GifDbHelper

    override val data1 = GifData.Impl(
        id = "1",
        url = "url1",
        urlWebp = "urlWebp1",
        trendingDatetime = 1
    )

    override val data2 = GifData.Impl(
        id = "2",
        url = "url2",
        urlWebp = "urlWebp2",
        trendingDatetime = 2
    )

    override val data1Id: String = data1.id



    @BeforeTest
    override fun setUp() {
        super.setUp()
        dbHelper = GifDbHelperImpl(db!!)
    }


}

