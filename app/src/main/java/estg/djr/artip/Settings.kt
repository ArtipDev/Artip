package estg.djr.artip

import android.icu.number.NumberFormatter.with
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.animation.expandHorizontally
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.google.android.gms.common.util.CollectionUtils.listOf
import estg.djr.artip.ui.theme.ArtipTheme

class Settings : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtipTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Settings("Android")
                }
            }
        }
    }
}

@Composable
fun Settings(name: String) {
    Text(text=name)
}

@Composable
fun LanguageSelection() {
    val radioOptions = ArrayList<String>()
    radioOptions.add("ENG")
    radioOptions.add("PT")
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[2])}
}