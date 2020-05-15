package co.lucaspinazzola.example.ui.main

import androidx.compose.Composable
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.Box
import androidx.ui.foundation.Text
import androidx.ui.graphics.Color
import androidx.ui.layout.*
import androidx.ui.material.Button
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp


interface Select {

    companion object {
        @Composable
        fun Content(navigateToGiphy: () -> Unit, navigateToRickAndMorty: () -> Unit) {
            Column(
                modifier = Modifier.fillMaxHeight().fillMaxWidth().padding(40.dp),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Box(backgroundColor = Color.Red,
                    modifier = Modifier.fillMaxWidth().preferredHeight(40.dp)
                )
                Button(onClick = { navigateToGiphy() },
                    modifier = Modifier.gravity(Alignment.CenterHorizontally)) {
                    Text(text = "Giphy")
                }
                Button(onClick = { navigateToRickAndMorty() },
                    modifier = Modifier.gravity(Alignment.CenterHorizontally)) {
                    Text(text = "Rick And Morty")
                }
                Box(backgroundColor = Color.Red,
                    modifier = Modifier.fillMaxWidth().preferredHeight(40.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun ProfilePreview() {
    Select.Content({},{})
}
