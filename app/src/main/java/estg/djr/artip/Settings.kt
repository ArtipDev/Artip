package estg.djr.artip

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import estg.djr.artip.ui.theme.ArtipTheme
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import estg.djr.artip.data.SavePrefNotifications
import estg.djr.artip.data.SavePrefRadius
import estg.djr.artip.ui.theme.Artip_pink
import estg.djr.artip.ui.theme.bg_main
import kotlinx.coroutines.launch
import com.google.firebase.firestore.DocumentReference
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import estg.djr.artip.data.SavePrefUserType


private lateinit var mAuth: FirebaseAuth
var db = FirebaseFirestore.getInstance();



class Settings : ComponentActivity() {
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

fun changeValue(isArtist: Boolean) {

    val washingtonRef = db.collection("users").document("LCYsGy2TriR4idgzo0Fln3mknos2")

    washingtonRef
        .update("isArtist", isArtist)
        .addOnSuccessListener { Log.d("TAG", "DocumentSnapshot successfully updated!") }
        .addOnFailureListener { e -> Log.w("TAG", "Error updating document", e) }

}


@Composable
fun SettingsCompo(visible: Boolean) {

    val context = LocalContext.current



    val scope = rememberCoroutineScope()
    val dataRadius = SavePrefRadius(context)
    val dataNotifications = SavePrefNotifications(context)
    val dataUserType = SavePrefUserType(context)

    var radius by rememberSaveable { mutableStateOf("") }

    val savedRadius = dataRadius.getRadiusPref.collectAsState(initial = "")
    val savedNotifications = dataNotifications.getNotPref.collectAsState(initial = false)
    val savedUserType = dataUserType.getUserTypePref.collectAsState(initial = false)



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
                    horizontalAlignment = CenterHorizontally
                ) {
                    Text(text = "Search Radius",
                        modifier = Modifier
                            .padding(5.dp)
                            .wrapContentHeight(CenterVertically),
                        color = Color.White)
                }

                TextField(value = savedRadius.value!!,
                    onValueChange = {scope.launch {
                        dataRadius.saveRadius(it)
                    }},
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



            Switch(
                checked = savedUserType.value!!,
                onCheckedChange = {
                    scope.launch {dataUserType.saveUserTypePref(!savedUserType.value!!) }
                    if(it) {
                        changeValue(true)
                    } else {
                        changeValue(false)
                    }

                                  },
                colors = SwitchDefaults.colors(Color.Green)
            )



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
                Checkbox(checked = savedNotifications.value!!, onCheckedChange = {
                    scope.launch { dataNotifications.saveNotifications(!savedNotifications.value!!) }
                })
            }



    }
    }
}

@Preview
@Composable
fun LanguageSelection() {
    SettingsCompo(visible = true)

}