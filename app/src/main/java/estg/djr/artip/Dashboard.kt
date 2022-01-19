package estg.djr.artip

import android.R
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
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

    private val sharedPrefFile = "preferences"
    val nv = Navbar()
    lateinit var sharedPreferences: SharedPreferences
    lateinit var activity: Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = this.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        activity = this

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
            Arrays.asList(PostData("123123","JohnDoe", "Um doid três", "https://lh3.googleusercontent.com/a/AATXAJyEBAfnbxsRDXsmqzTETt6A7vhrzVqIQIA9yAMx=s96-c"),
                PostData("123123","JohnDoe", "Um doid três", "https://lh3.googleusercontent.com/a/AATXAJyEBAfnbxsRDXsmqzTETt6A7vhrzVqIQIA9yAMx=s96-c"),
                PostData("123123","JohnDoe", "Um doid três", "https://lh3.googleusercontent.com/a/AATXAJyEBAfnbxsRDXsmqzTETt6A7vhrzVqIQIA9yAMx=s96-c"))

        var vi: Boolean = true
        val currentTab : Int = tabChange.tab.value
        Scaffold(
            bottomBar = { BottomNavigationBar(nv) },
        ) {
            when(currentTab) {
                0 -> {
                    GoogleMap(visible = true)
                    ProfileCompo(false)
                    SettingsCompo(visible = false)
                    FeedCompo(visible = false,list)
                }
                1 -> {
                    GoogleMap(visible = false)
                    ProfileCompo(visible = false)
                    SettingsCompo(visible = false)
                    FeedCompo(true,list)
                }
                2 -> {
                    FeedCompo(visible = false,list)
                    ProfileCompo(true)
                    GoogleMap(visible = false)
                    SettingsCompo(visible = false)
                }
                4 -> {
                    SettingsCompo(visible = true)
                    FeedCompo(visible = false, list)
                    GoogleMap(visible = false)
                    ProfileCompo(visible = false)
                }
            }
        }
    }
}