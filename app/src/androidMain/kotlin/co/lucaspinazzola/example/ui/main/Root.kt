package co.lucaspinazzola.example.ui.main

import androidx.compose.Composable
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
            Router(defaultRouting) { backStack ->
                fun Routing.navigateTo(routing: Routing) : () -> Unit = {
                    backStack.push(routing)
                }
                when (val currentRouting = backStack.last()) {
                    Routing.Select -> Select.Content(
                        navigateToGiphy = currentRouting.navigateTo(Routing.Giphy),
                        navigateToRickAndMorty = currentRouting.navigateTo(Routing.RickAndMorty)
                    )
                    Routing.Giphy -> null
                    Routing.RickAndMorty -> null
                }
            }
        }
    }
}