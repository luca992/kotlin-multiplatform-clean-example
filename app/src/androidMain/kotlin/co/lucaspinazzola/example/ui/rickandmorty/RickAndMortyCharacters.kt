package co.lucaspinazzola.example.ui.rickandmorty

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
import androidx.ui.foundation.ScrollerPosition
import androidx.ui.foundation.VerticalScroller
import androidx.ui.graphics.ImageAsset
import androidx.ui.graphics.asImageAsset
import androidx.ui.layout.*
//import androidx.ui.layout.Table
import androidx.ui.livedata.observeAsState
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.IntPx
import androidx.ui.unit.dp
import androidx.ui.unit.toPx
import co.lucaspinazzola.example.ExampleApplication
import co.lucaspinazzola.example.R
import co.lucaspinazzola.example.di.component.ViewComponent
import co.lucaspinazzola.example.domain.model.Img
import co.lucaspinazzola.example.ui.base.ComposableView
import co.lucaspinazzola.example.ui.factory.ViewModelFactory
import coil.Coil
import coil.request.LoadRequest
import coil.request.LoadRequestBuilder
import coil.size.Scale
import coil.target.Target
import com.luca992.compose.image.CoilImage
import com.squareup.inject.assisted.AssistedInject
import dev.icerock.moko.mvvm.livedata.asFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class RickAndMortyCharacters(val vm: RickAndMortyCharactersViewModel): ComposableView {
        //val ScrollerPosition.isAtEndOfList: Boolean get() = value >= maxPosition

        @InternalCoroutinesApi
        @Composable
        override fun Content() {
            val context = ContextAmbient.current
            val pairedImgs = remember{ ModelList<Pair<Img,Img>>() }

            val imgs = remember {
                GlobalScope.launch(Dispatchers.Main) {
                    vm.imgs.asFlow().collect { imgs:List<Img> ->
                        pairedImgs.clear()
                        for (i in imgs.indices step 2) {
                            pairedImgs.add(Pair(imgs[i], imgs[i + 1]))
                        }
                    }
                }
            }

            if (pairedImgs.isNotEmpty()) {
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
                    }
                )
            }
            /*
            val scrollerPosition = ScrollerPosition()
            Observe {
                onCommit(scrollerPosition.isAtEndOfList) {
                    if (scrollerPosition.isAtEndOfList){
                        vm.loadNextPage()
                    }
                }
            }
            VerticalScroller( scrollerPosition = scrollerPosition) {
                if (imgs.value!!.isNotEmpty()) {
                    for (i in 0 until 4) {
                        val img = imgs.value!![i]
                        CoilImage(img.url) {
                            placeholder(
                                ResourcesCompat.getDrawable(
                                    context.resources,
                                    R.drawable.ic_search_black_24dp,
                                    null
                                )
                            )
                        }
                    }*/
                    /*imgs.value?.forEach{ img->
                        CoilImage(img.url) {
                            placeholder(
                                ResourcesCompat.getDrawable(
                                    context.resources,
                                    R.drawable.ic_search_black_24dp,
                                    null
                                )
                            )
                    }*/
                    /*
                    Table was temporarily removed in dev11
                    Table(columns = 2) {
                        for (i in 0 until imgs.value!!.size / 2) {
                            tableRow {
                                for (j in 0 until 2) {
                                    val img = imgs.value!![i * 2 + j]
                                    CoilImage(img.url) {
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
                        }
                    }*/
                }
            //}
        //}

}

@Preview
@Composable
fun ProfilePreview() {
    val context = ContextAmbient.current
    val rmc = ViewComponent.Initializer.init((context.applicationContext as ExampleApplication).mainComponent).composableViewByClass()[RickAndMortyCharacters::class.java]
    rmc?.Content()
}

