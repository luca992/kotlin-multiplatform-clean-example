package co.lucaspinazzola.example.ui.rickandmorty

import androidx.compose.*
import androidx.core.content.res.ResourcesCompat
import androidx.ui.core.ContextAmbient
import androidx.ui.core.Modifier
import androidx.ui.foundation.AdapterList
import androidx.ui.foundation.ScrollerPosition
import androidx.ui.foundation.VerticalScroller
import androidx.ui.layout.fillMaxWidth
import androidx.ui.layout.height
//import androidx.ui.layout.Table
import androidx.ui.livedata.observeAsState
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import co.lucaspinazzola.example.ExampleApplication
import co.lucaspinazzola.example.R
import co.lucaspinazzola.example.di.component.ViewComponent
import co.lucaspinazzola.example.ui.base.ComposableView
import co.lucaspinazzola.example.ui.factory.ViewModelFactory
import com.luca992.compose.image.CoilImage
import com.squareup.inject.assisted.AssistedInject


class RickAndMortyCharacters(val vm: RickAndMortyCharactersViewModel): ComposableView {
        //val ScrollerPosition.isAtEndOfList: Boolean get() = value >= maxPosition

        @Composable
        override fun Content() {
            val context = ContextAmbient.current
            val imgs = vm.imgs.ld().observeAsState()
            if (imgs.value!!.isNotEmpty()) {
                AdapterList(
                    data = imgs.value!!,
                    itemCallback = { img ->
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
