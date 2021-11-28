package estg.djr.artip

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import estg.djr.artip.ui.theme.ArtipTheme

class Dashboard : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ArtipTheme {
                MainPage()
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun MainPage() {
        Scaffold(
            bottomBar = { BottomNavigationBar() }
        ) {
            //Umacacos peulods
        }
    }
}

