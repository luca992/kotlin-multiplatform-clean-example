
import co.lucaspinazzola.example.data.db.helper.ImgDbHelper
import co.lucaspinazzola.example.data.db.helper.ImgDbHelperImpl
import co.lucaspinazzola.example.data.model.ImgData
import kotlin.test.BeforeTest


class GifDbHelperImplTest: DbHelperTest<ImgData, ImgDbHelper>() {


    override lateinit var dbHelper : ImgDbHelper

    override val data1 = ImgData.Impl(
        id = "1",
        resultIndex = 1,
        url = "url1"
    )

    override val data2 = ImgData.Impl(
        id = "2",
        resultIndex = 2,
        url = "url2"
    )

    override val data1Id: String = data1.id



    @BeforeTest
    override fun setUp() {
        super.setUp()
        dbHelper = ImgDbHelperImpl(db!!)
    }


}

