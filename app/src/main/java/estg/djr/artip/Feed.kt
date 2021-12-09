package estg.djr.artip

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
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
                FeedEntry("Samuel Fox", "Hello guys Ill be doing some magic tricks today in aliados.")
                FeedEntry("Robert Sha256", "10001 10 10001 100010101 01001 10010100 10101 100101 101 101010")
                FeedEntry("Cunimbriga Astúria", "Soy astúriana e portuguesa pero no ablo portugues.")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview4() {
    ArtipTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = { InputMessage() }
        ) {
            FeedCompo(true)
        }
    }
}

@Composable
fun InputMessage() {
    TextField(value = "Hello guys...", onValueChange = {},
    modifier = Modifier.fillMaxWidth())
}