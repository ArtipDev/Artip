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

class Profile : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtipTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    ProfileCompo(true)
                }
            }
        }
    }
}

@Composable
fun ProfileCompo(visible: Boolean) {
    if(visible) {
        Text(text = "Hello!")
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview5() {
    ArtipTheme {
        ProfileCompo(true)
    }
}