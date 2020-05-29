package co.lucaspinazzola.example.ui.giphy

import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import androidx.compose.*
import androidx.compose.frames.ModelList
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.ui.core.ContextAmbient
import androidx.ui.core.DensityAmbient
import androidx.ui.core.Modifier
import androidx.ui.core.WithConstraints
import androidx.ui.foundation.AdapterList
import androidx.ui.foundation.Image
import androidx.ui.graphics.ImageAsset
import androidx.ui.graphics.asImageAsset
import androidx.ui.layout.Row
import androidx.ui.layout.width
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.IntPx
import androidx.ui.unit.dp
import co.lucaspinazzola.example.ExampleApplication
import co.lucaspinazzola.example.R
import co.lucaspinazzola.example.di.component.ViewComponent
import co.lucaspinazzola.example.domain.model.Img
import co.lucaspinazzola.example.ui.base.ComposableView
import coil.Coil
import coil.request.LoadRequest
import coil.request.LoadRequestBuilder
import coil.size.Scale
import dev.icerock.moko.mvvm.livedata.asFlow
import kotlinx.coroutines.flow.collect
import coil.target.Target
import com.luca992.compose.image.CoilImage
import kotlinx.coroutines.*


class GiphyComp(val vm: GiphyViewModel): ComposableView {

        @Composable
        override fun Content() {
            val context = ContextAmbient.current
            val pairedImgs = remember { ModelList<Pair<Img, Img>>() }
            remember {
                vm.query.value = "test"
            }
            remember {
                GlobalScope.launch(Dispatchers.Main) {
                    vm.gifs.asFlow().collect { imgs: List<Img> ->
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
                            val w = with(DensityAmbient.current) { (constraints.maxWidth.toDp().value / 2).dp }
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
    val view = ViewComponent.Initializer.init((context.applicationContext as ExampleApplication).mainComponent).composableViewByClass()[GiphyComp::class.java]
    view?.Content()
}
