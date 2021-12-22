package estg.djr.artip

import android.icu.number.NumberFormatter.with
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.animation.expandHorizontally
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.common.util.CollectionUtils.listOf
import estg.djr.artip.ui.theme.ArtipTheme
import android.R
import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import estg.djr.artip.ui.theme.Artip_pink
import estg.djr.artip.ui.theme.bg_main


class Settings : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
8


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
fun SettingsCompo(visible: Boolean, editor: SharedPreferences.Editor) {

    var notifications by remember { mutableStateOf(false) }
    val text = remember { mutableStateOf(TextFieldValue("")) }

    if(visible){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(bg_main)
                .wrapContentSize(Center)
                .fillMaxSize()
                .padding(20.dp)
        ){
            Row(modifier = Modifier.padding(40.dp)){
                Text(text = "Settings",
                Modifier.wrapContentHeight(CenterVertically),
                    color = Artip_pink,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold)
            }
            Row(
                Modifier
                    .wrapContentHeight(CenterVertically)
                    .padding(5.dp),
                    ){
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Search Radius",
                        modifier = Modifier
                            .padding(5.dp)
                            .wrapContentHeight(CenterVertically),
                        color = Color.White)
                }

                TextField(value = text.value,
                    onValueChange = {text.value = it},
                modifier = Modifier
                    .wrapContentWidth(CenterHorizontally)
                    .padding(10.dp, 0.dp, 10.dp, 0.dp)
                    .width(60.dp))
                Text(text = "KM",
                    modifier = Modifier
                        .padding(5.dp)
                        .wrapContentHeight(CenterVertically),
                    color = Color.White)
            }
            Spacer(modifier = Modifier.size(20.dp))
            Row(
                Modifier
                    .wrapContentHeight(CenterVertically)
                    .padding(5.dp, 20.dp, 5.dp, 20.dp)) {
                Text(text = "Send Notifications",
                    modifier = Modifier
                        .padding(5.dp)
                        .wrapContentHeight(CenterVertically),
                    color = Color.White)
                Checkbox(checked = notifications, onCheckedChange = {notifications = !notifications})
            }
            Row(){
                Button(onClick = {
                    val num = text.component1().text.toInt()
                    editor.putBoolean("notificationOn", notifications)
                    editor.putInt("radius", num)
                    editor.apply()
                    editor.commit()
                }) {
                    Text(text = "Salvar")
                }
            }
    }
    }
}

@Preview
@Composable
fun LanguageSelection() {


}