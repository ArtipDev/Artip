package estg.djr.artip

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import estg.djr.artip.ui.theme.ArtipTheme
import estg.djr.artip.ui.theme.bg_main

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtipTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = theme.resources.getColor(bg_main)) {
                   
                }
            }
        }
    }
}

@Composable
fun Greeting2(name : String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    ArtipTheme {
        Greeting2("Android")
    }
}