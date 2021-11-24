package estg.djr.artip

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.RoundedCorner
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import estg.djr.artip.ui.theme.ArtipTheme
import estg.djr.artip.ui.theme.NavButton
import estg.djr.artip.ui.theme.SelectedNavButton
import estg.djr.artip.ui.theme.bg_main
import org.intellij.lang.annotations.JdkConstants

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtipTheme {
            }
        }
    }
}

@Composable
fun Greeting2(name : String) {
    Text(text = "Hello $name!")
}

@Composable
fun LoginScreen() {
    Column(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = bg_main)

    ) {
        Spacer(modifier = Modifier.size(40.dp))
        Image(painter = painterResource(R.drawable.artlogo), "Logov",
            Modifier
                .fillMaxWidth()
                .padding(0.dp)
                .size(250.dp))
        Row(
            Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.welcome_message),
                fontFamily = FontFamily(Font(R.font.worksansregular)),
                fontSize = 30.sp,
                color = SelectedNavButton)
        }
        Spacer(modifier = Modifier.size(20.dp))
        Row(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(color = NavButton)
                .padding(20.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Column(modifier = Modifier.padding(10.dp)){
                Row(Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center) {
                    Text(text = stringResource(id = R.string.login_message),
                        fontFamily = FontFamily(Font(R.font.worksansregular)),
                    fontSize = 20.sp,
                    color = bg_main)
                    Spacer(modifier = Modifier.size(5.dp))
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Column {
                        Box(
                            Modifier
                                .padding(10.dp)
                                .clip(shape = RoundedCornerShape(50))
                        )
                        {
                            Image(painter = painterResource(id = R.drawable.g), contentDescription = "google"
                                ,
                                Modifier
                                    .size(80.dp)
                                    .background(color = bg_main)
                                    .padding(10.dp))
                        }
                    }
                }
                Spacer(modifier = Modifier.size(40.dp))
                Column(modifier = Modifier.background(color = SelectedNavButton).fillMaxWidth().fillMaxHeight()
                    ) {
                    Row(Modifier.fillMaxWidth()
                        .clip(shape = RoundedCornerShape(20.dp))
                        .padding(10.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(text = stringResource(id = R.string.register_message),
                            fontFamily = FontFamily(Font(R.font.worksansregular)),
                            fontSize = 20.sp,
                            color = bg_main)
                        Spacer(modifier = Modifier.size(5.dp))
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Column {
                            Box(
                                Modifier
                                    .padding(10.dp)
                                    .clip(shape = RoundedCornerShape(50))
                            )
                            {
                                Image(painter = painterResource(id = R.drawable.g), contentDescription = "google"
                                    ,
                                    Modifier
                                        .size(80.dp)
                                        .background(color = bg_main)
                                        .padding(10.dp))
                            }
                        }
                    }

                }

            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    ArtipTheme {
        LoginScreen()
    }
}