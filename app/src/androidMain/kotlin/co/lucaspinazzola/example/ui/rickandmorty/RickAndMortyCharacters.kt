package co.lucaspinazzola.example.ui.rickandmorty

import androidx.compose.Composable
import androidx.ui.foundation.VerticalScroller
import androidx.ui.layout.Column
import androidx.ui.livedata.observeAsState
import androidx.ui.tooling.preview.Preview
import co.lucaspinazzola.example.ExampleApplication
import co.lucaspinazzola.example.data.utils.application
import co.lucaspinazzola.example.di.component.FragmentComponent
import co.lucaspinazzola.example.ui.composables.CoilImage


interface RickAndMortyCharacters {

    companion object {
        @Composable
        fun Content() {
            val vm = FragmentComponent.Initializer.init((application as ExampleApplication).mainComponent).factory.create(RickAndMortyCharactersViewModel::class.java)
            val imgs = vm.imgs.ld().observeAsState()
            VerticalScroller {
                Column {
                    imgs.value?.forEach { img ->
                        CoilImage(img.url)
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

