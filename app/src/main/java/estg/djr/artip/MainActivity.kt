package estg.djr.artip

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import estg.djr.artip.ui.theme.ArtipTheme


class MainActivity : AppCompatActivity(){

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