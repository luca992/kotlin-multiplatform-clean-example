package co.lucaspinazzola.example.ui.rickandmorty

import androidx.compose.Composable
import androidx.core.content.res.ResourcesCompat
import androidx.ui.core.ContextAmbient
import androidx.ui.core.Modifier
import androidx.ui.foundation.VerticalScroller
import androidx.ui.layout.Column
import androidx.ui.layout.height
import androidx.ui.layout.width
import androidx.ui.livedata.observeAsState
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import co.lucaspinazzola.example.ExampleApplication
import co.lucaspinazzola.example.R
import co.lucaspinazzola.example.di.component.FragmentComponent
import co.lucaspinazzola.example.ui.composables.CoilImage
import co.lucaspinazzola.example.ui.utils.LoadingDrawable


interface RickAndMortyCharacters {

    companion object {
        @Composable
        fun Content() {
            val context = ContextAmbient.current
            val vm = FragmentComponent.Initializer.init((context.applicationContext as ExampleApplication).mainComponent).factory.create(RickAndMortyCharactersViewModel::class.java)
            val imgs = vm.imgs.ld().observeAsState()
            VerticalScroller {
                Column {
                    imgs.value?.forEach { img ->
                        CoilImage(img.url){
                            placeholder(ResourcesCompat.getDrawable(context.resources, R.drawable.ic_search_black_24dp, null))
                        }//LoadingDrawable(context))}
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ProfilePreview() {
    RickAndMortyCharacters.Content()
}

