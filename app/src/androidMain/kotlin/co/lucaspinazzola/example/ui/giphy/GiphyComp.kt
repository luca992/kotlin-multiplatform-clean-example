package co.lucaspinazzola.example.ui.giphy

import androidx.compose.Composable
import androidx.compose.frames.ModelList
import androidx.compose.onActive
import androidx.compose.remember
import androidx.core.content.res.ResourcesCompat
import androidx.ui.core.ContextAmbient
import androidx.ui.core.DensityAmbient
import androidx.ui.core.Modifier
import androidx.ui.core.WithConstraints
import androidx.ui.foundation.AdapterList
import androidx.ui.foundation.TextField
import androidx.ui.foundation.lazy.LazyColumnItems
import androidx.ui.graphics.Color
import androidx.ui.input.KeyboardType
import androidx.ui.input.TextFieldValue
import androidx.ui.layout.Column
import androidx.ui.layout.Row
import androidx.ui.layout.padding
import androidx.ui.layout.width
import androidx.ui.livedata.observeAsState
import androidx.ui.text.TextRange
import androidx.ui.text.TextStyle
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import androidx.ui.unit.sp
import co.lucaspinazzola.example.R
import co.lucaspinazzola.example.di.component.AppComponent
import co.lucaspinazzola.example.di.component.ViewComponent
import co.lucaspinazzola.example.domain.model.Img
import co.lucaspinazzola.example.ui.base.ComposableView
import co.lucaspinazzola.example.ui.utils.LoadingDrawable
import com.luca992.compose.image.CoilImage
import dev.icerock.moko.mvvm.livedata.asFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class GiphyComp(val vm: GiphyViewModel): ComposableView {

        @Composable
        override fun Content() {
            val context = ContextAmbient.current
            val gifs =  vm.gifs.ld().observeAsState(emptyList())
            val columns = 2
            val chunked = gifs.value.chunked(columns)
            val query = vm.query.ld().observeAsState()

            Column {

                TextField(
                    modifier = Modifier.padding(16.dp),
                    value = TextFieldValue(
                        query.value ?: "",
                        TextRange(query.value?.length ?: 0, query.value?.length ?: 0)
                    ),
                    onValueChange = { vm.query.value = it.text },
                    textStyle = TextStyle(
                        fontSize = (20.sp),
                        color = Color.DarkGray
                    ),
                    keyboardType = KeyboardType.Text
                )
                LazyColumnItems(chunked) { imgs ->
                    WithConstraints {
                        Row {
                            val w = with(DensityAmbient.current) { (constraints.maxWidth.toDp().value / columns).dp }
                            imgs.forEach {
                                CoilImage(it.url, Modifier.width(w)) {
                                    placeholder(LoadingDrawable(context))
                                }
                            }
                        }
                    }
                    onActive {
                        if (chunked.lastOrNull() === imgs) {
                            //at end of list
                            vm.loadNextPage()
                        }
                    }
                }

            }
        }
}

@Preview
@Composable
fun ProfilePreview() {
    val mainComponent = AppComponent.init()
    val view = ViewComponent.Initializer.init(mainComponent).composableViewByClass()[GiphyComp::class.java]
    view!!.Content()
}
