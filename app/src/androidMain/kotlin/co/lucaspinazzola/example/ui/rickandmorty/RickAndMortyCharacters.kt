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
import co.lucaspinazzola.example.ui.utils.LoadingDrawable
import com.luca992.compose.image.CoilImage


@Composable
fun RickAndMortyCharactersComp(vm: RickAndMortyCharactersViewModel) {
    val context = ContextAmbient.current
    val imgs =  vm.imgs.ld().observeAsState(emptyList())
    val columns = 2
    val chunked = imgs.value.chunked(columns)
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


@Preview
@Composable
fun ProfilePreview() {
    val viewComponent = ViewComponent.Initializer.init(AppComponent.init())
    val vmf = viewComponent.factory
    RickAndMortyCharactersComp(vmf.create(RickAndMortyCharactersViewModel::class.java))
}

