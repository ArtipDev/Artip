package estg.djr.artip

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.skydoves.landscapist.glide.GlideImage
import estg.djr.artip.dataclasses.PostData
import estg.djr.artip.ui.theme.ArtipTheme
import estg.djr.artip.ui.theme.Artip_pink

class FeedEntry : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtipTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {

                }
            }
        }
    }
}

@Composable
fun Feed_Entry(pd: PostData) {
    Column(
        Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .wrapContentHeight()) {
        Row(
            Modifier
                .background(color = Artip_pink)
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(10.dp))) {

          /*  Image(painter = painterResource(id = R.drawable.profile_icon), "test",
                modifier = Modifier
                    .size(90.dp)
                    .padding(10.dp)
                    .clip(shape = CircleShape))*/

           /* GlideImage(
                imageModel = "https://lh3.googleusercontent.com/a/AATXAJyEBAfnbxsRDXsmqzTETt6A7vhrzVqIQIA9yAMx=s96-c",
                // Crop, Fit, Inside, FillHeight, FillWidth, None
                contentScale = ContentScale.Crop,
                // shows an image with a circular revealed animation.
                circularRevealedEnabled = true,
                // shows a placeholder ImageBitmap when loading.
                placeHolder = ImageBitmap.imageResource(R.drawable.artlogo),
                // shows an error ImageBitmap when the request failed.
                error = ImageBitmap.imageResource(R.drawable.account)
            )*/

            GlideImage(
                imageModel = pd.userPhoto,
                modifier = Modifier
                    .size(90.dp)
                    .padding(10.dp)
                    .clip(shape = CircleShape),
                error = ImageBitmap.imageResource(R.drawable.profile_icon)
            )



            Column() {
                Text(
                    modifier = Modifier.padding(10.dp),
                    text = pd.name,
                    fontWeight = FontWeight.Bold
                )
                Text(modifier = Modifier.padding(10.dp, 0.dp, 20.dp, 10.dp),
                    textAlign = TextAlign.Justify,
                    text = pd.text)
            }
            
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview6() {
    ArtipTheme {
        //FeedEntry("Android", "ahhh")
    }
}