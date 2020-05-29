package co.lucaspinazzola.example.ui.main

import androidx.compose.Composable
import androidx.ui.core.ContextAmbient
import co.lucaspinazzola.example.ExampleApplication
import co.lucaspinazzola.example.di.component.ViewComponent
import co.lucaspinazzola.example.ui.giphy.GiphyComp
import co.lucaspinazzola.example.ui.rickandmorty.RickAndMortyCharacters
import co.lucaspinazzola.example.ui.rickandmorty.RickAndMortyCharactersViewModel
import com.github.zsoltk.compose.router.Router

interface Root {

    sealed class Routing {
        object Select: Routing()
        object Giphy: Routing()
        object RickAndMorty: Routing()
    }

    companion object {
        @Composable
        fun Content(defaultRouting: Routing) {
            val context = ContextAmbient.current
            val composables = ViewComponent.Initializer.init((context.applicationContext as ExampleApplication).mainComponent).composableViewByClass()
            Router(defaultRouting) { backStack ->
                fun Routing.navigateTo(routing: Routing) : () -> Unit = {
                    backStack.push(routing)
                }
                when (val currentRouting = backStack.last()) {
                    Routing.Select -> Select.Content(
                        navigateToGiphy = currentRouting.navigateTo(Routing.Giphy),
                        navigateToRickAndMorty = currentRouting.navigateTo(Routing.RickAndMorty)
                    )
                    Routing.Giphy -> composables[GiphyComp::class.java]?.Content()
                    Routing.RickAndMorty -> composables[RickAndMortyCharacters::class.java]?.Content()
                }
            }
        }
    }
}