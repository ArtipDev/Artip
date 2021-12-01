package estg.djr.artip

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import estg.djr.artip.ui.theme.ArtipTheme

class FeedEntry : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtipTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    FeedEntry("Android")
                }
            }
        }
    }
}

@Composable
fun FeedEntry(name: String) {
    Column(
        Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .wrapContentHeight()) {
        Row(Modifier.background(Color.Green)) {
            Image(painter = painterResource(id = R.drawable.profile_icon), "test",
                modifier = Modifier.size(64.dp)
                    .padding(10.dp)
                    .clip(shape = CircleShape))
            Text(text = "Subject 2031")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview6() {
    ArtipTheme {
        FeedEntry("Android")
    }
}