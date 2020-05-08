package co.lucaspinazzola.example.ui.main

import androidx.compose.Composable
import com.github.zsoltk.compose.router.Router

interface Root {

    sealed class Routing {
        object Select: Routing()
    }

    companion object {
        @Composable
        fun Content(defaultRouting: Routing) {
            Router(defaultRouting) { backStack ->
                when (val currentRouting = backStack.last()) {
                    is Routing.Select -> Select.Content()
                }
            }
        }
    }
}