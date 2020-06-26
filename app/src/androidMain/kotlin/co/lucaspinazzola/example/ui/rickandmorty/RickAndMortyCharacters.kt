package co.lucaspinazzola.example.ui.rickandmorty

import androidx.compose.*
import androidx.core.content.res.ResourcesCompat
import androidx.ui.core.ContextAmbient
import androidx.ui.core.DensityAmbient
import androidx.ui.core.Modifier
import androidx.ui.core.WithConstraints
import androidx.ui.foundation.AdapterList
import androidx.ui.foundation.lazy.LazyColumnItems
import androidx.ui.layout.Row
import androidx.ui.layout.width
import androidx.ui.livedata.observeAsState
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import co.lucaspinazzola.example.ExampleApplication
import co.lucaspinazzola.example.R
import co.lucaspinazzola.example.di.component.AppComponent
import co.lucaspinazzola.example.di.component.ViewComponent
import co.lucaspinazzola.example.domain.model.Img
import com.luca992.compose.image.CoilImage


@Composable
fun RickAndMortyCharactersComp(vm: RickAndMortyCharactersViewModel) {
    val context = ContextAmbient.current
    val imgs =  vm.imgs.ld().observeAsState(emptyList())
    val chunked = imgs.value.chunked(2)
    LazyColumnItems(chunked) { imgPair ->
        WithConstraints {
            Row {
                val w = with(DensityAmbient.current) { (constraints.maxWidth.toDp().value / 2).dp }
                CoilImage(imgPair[0].url, Modifier.width(w)) {
                    placeholder(
                        ResourcesCompat.getDrawable(
                            context.resources,
                            R.drawable.ic_search_black_24dp,
                            null
                        )
                    )
                }
                CoilImage(imgPair[1].url, Modifier.width(w)) {
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
            if (chunked.lastOrNull() === imgPair) {
                //at end of list
                vm.loadNextPage()
            }
        }
    }
}


@Preview
@Composable
fun ProfilePreview() {
    val viewComponent = ViewComponent.Initializer.init(AppComponent.init())
    val vmf = viewComponent.factory
    RickAndMortyCharactersComp(vmf.create(RickAndMortyCharactersViewModel::class.java))
}

