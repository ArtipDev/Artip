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
import estg.djr.artip.dataclasses.PostData
import estg.djr.artip.ui.theme.ArtipTheme
import java.util.*

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
        val list: List<PostData> =
            Arrays.asList(PostData("JohnDoe", "Um doid três"),
                PostData("JohnDoe", "Um doid três"),
                PostData("JohnDoe", "Um doid três"))

        var vi: Boolean = true
        val currentTab : Int = tabChange.tab.value
        Scaffold(
            bottomBar = { BottomNavigationBar(nv) },
        ) {
            when(currentTab) {
                0 -> {
                    GoogleMap(visible = true)
                    ProfileCompo(false)
                    FeedCompo(visible = false,list)
                }
                1 -> {
                    GoogleMap(visible = false)
                    ProfileCompo(visible = false)
                    FeedCompo(true,list)
                }
                2 -> {
                    FeedCompo(visible = false,list)
                    ProfileCompo(true)
                    GoogleMap(visible = false)
                }
            }
        }
    }
}