package co.lucaspinazzola.example.ui.rickandmorty

//import androidx.ui.layout.Table
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
import androidx.ui.layout.Row
import androidx.ui.layout.width
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import co.lucaspinazzola.example.ExampleApplication
import co.lucaspinazzola.example.R
import co.lucaspinazzola.example.di.component.ViewComponent
import co.lucaspinazzola.example.domain.model.Img
import co.lucaspinazzola.example.ui.base.ComposableView
import com.luca992.compose.image.CoilImage
import dev.icerock.moko.mvvm.livedata.asFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class RickAndMortyCharacters(val vm: RickAndMortyCharactersViewModel): ComposableView {

        @InternalCoroutinesApi
        @Composable
        override fun Content() {
            val context = ContextAmbient.current
            val pairedImgs = remember { ModelList<Pair<Img, Img>>() }

            remember {
                GlobalScope.launch(Dispatchers.Main) {
                    vm.imgs.asFlow().collect { imgs: List<Img> ->
                        pairedImgs.clear()
                        for (i in imgs.indices step 2) {
                            pairedImgs.add(Pair(imgs[i], imgs[i + 1]))
                        }
                    }
                }
            }

            AdapterList(
                data = pairedImgs,
                itemCallback = { imgPair ->
                    WithConstraints {
                        Row {
                            val w =
                                with(DensityAmbient.current) { (constraints.maxWidth.toDp().value / 2).dp }
                            CoilImage(imgPair.first.url, Modifier.width(w)) {
                                placeholder(
                                    ResourcesCompat.getDrawable(
                                        context.resources,
                                        R.drawable.ic_search_black_24dp,
                                        null
                                    )
                                )
                            }
                            CoilImage(imgPair.second.url, Modifier.width(w)) {
                                placeholder(
                                    ResourcesCompat.getDrawable(
                                        context.resources,
                                        R.drawable.ic_search_black_24dp,
                                        null
                                    )
                                )
                            }
                        }
                    }
                    onActive {
                        if (pairedImgs.last() === imgPair) {
                            //at end of list
                            vm.loadNextPage()
                        }
                    }
                }
            )
        }
}

@Preview
@Composable
fun ProfilePreview() {
    val context = ContextAmbient.current
    val rmc = ViewComponent.Initializer.init((context.applicationContext as ExampleApplication).mainComponent).composableViewByClass()[RickAndMortyCharacters::class.java]
    rmc?.Content()
}

