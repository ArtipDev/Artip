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
import estg.djr.artip.ui.theme.ArtipTheme

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
                    SettingsCompo(visible = false, sharedPreferences.edit(), activity = activity)
                }
                1 -> {
                    GoogleMap(visible = false)
                    ProfileCompo(visible = false)
                    FeedCompo(true)
                    SettingsCompo(visible = false, sharedPreferences.edit(), activity = activity)
                }
                2 -> {
                    FeedCompo(visible = false)
                    ProfileCompo(true)
                    GoogleMap(visible = false)
                    SettingsCompo(visible = false, sharedPreferences.edit(), activity = activity)
                }
                4 -> {
                    SettingsCompo(visible = true, sharedPreferences.edit(), activity = activity)
                    FeedCompo(visible = false)
                    GoogleMap(visible = false)
                    ProfileCompo(visible = false)
                }

            }

        }
    }
}