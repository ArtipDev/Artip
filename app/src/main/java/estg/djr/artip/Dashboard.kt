package estg.djr.artip

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import estg.djr.artip.ui.theme.ArtipTheme

class Dashboard : ComponentActivity() {

    val nv = Navbar()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtipTheme {
                MainPage(nv)
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun MainPage(tabChange: Navbar = Navbar()) {
        var vi: Boolean = true
        val currentTab : Int = tabChange.tab.value
        Scaffold(
            bottomBar = { BottomNavigationBar(nv) },
        ) {
            when(currentTab) {
                0 -> {
                    GoogleMap(visible = true)
                    ProfileCompo(false)
                    FeedCompo(visible = false)
                }
                1 -> {
                    GoogleMap(visible = false)
                    ProfileCompo(visible = false)
                    FeedCompo(true)
                }
                2 -> {
                    FeedCompo(visible = false)
                    ProfileCompo(true)
                    GoogleMap(visible = false)

                }

            }

        }
    }
}