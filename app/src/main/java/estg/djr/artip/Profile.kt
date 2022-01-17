package estg.djr.artip

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import estg.djr.artip.ui.theme.ArtipTheme

class Profile : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtipTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    ProfileCompo(true)
                }
            }
        }
    }
}

@Composable
fun ProfileCompo(visible: Boolean) {
    if(visible) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
            .padding(10.dp)
            .border(1.dp, Color(100, 244, 244))){
            Box(modifier = Modifier.padding(10.dp)) {
                Image(
                    painterResource(id = R.drawable.profile_icon),
                    "Foto Perfil",
                    modifier = Modifier
                        .size(150.dp, 150.dp)
                        .fillMaxSize()
                        .clip(CircleShape)
                        .border(2.dp, Color.LightGray, CircleShape)
                )
            }
            Column (horizontalAlignment = Alignment.CenterHorizontally , verticalArrangement = Arrangement.Center,modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)){
                Text("João Freitas", fontSize = 30.sp)
                Text("Porto, Portugal", fontSize = 20.sp)
            }
            Row(modifier = Modifier.fillMaxWidth().padding(10.dp), horizontalArrangement = Arrangement.Center) {
                Row(){
                    Button(onClick = { /*TODO*/ }, modifier = Modifier.size(100.dp, 40.dp)) {
                        Text("Seguir")
                    }
                    Button(onClick = { /*TODO*/ }, modifier = Modifier.padding(horizontal = 13.dp).size(100.dp, 40.dp)) {
                        Text("?")
                    }
                    Button(onClick = { /*TODO*/ }, modifier = Modifier.size(100.dp, 40.dp)) {
                        Text("Donation")
                    }
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview5() {
    ArtipTheme {
        ProfileCompo(true)
    }
}