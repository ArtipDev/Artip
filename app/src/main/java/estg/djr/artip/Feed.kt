package estg.djr.artip

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import estg.djr.artip.ui.theme.ArtipTheme

class Feed : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtipTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    FeedCompo(true)
                }
            }
        }
    }
}

@Composable
fun FeedCompo(visible: Boolean) {
    if (visible) {
        Column() {

                FeedEntry(name = "")
                FeedEntry(name = "")
                FeedEntry(name = "")

        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview4() {
    ArtipTheme {
        FeedCompo(true)
    }
}