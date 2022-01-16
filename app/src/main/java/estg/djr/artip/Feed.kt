package estg.djr.artip

import android.Manifest
import android.app.Activity
import android.content.ContentProvider
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import com.google.android.gms.common.util.CollectionUtils.listOf
import com.google.android.gms.location.LocationServices
import estg.djr.artip.dataclasses.PostData
import estg.djr.artip.ui.theme.ArtipTheme
import estg.djr.artip.ui.theme.Artip_pink
import java.util.*
import kotlin.collections.ArrayList

class Feed : ComponentActivity() {
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
fun FeedCompo(visible: Boolean, postList: List<PostData>) {

    val context = LocalContext.current

    var l: Location?

    val longitude = remember { mutableStateOf(12.0) }
    val latitude = remember { mutableStateOf(-14.0) }

    val mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        // TODO: Consider calling
        //    ActivityCompat#requestPermissions
        // here to request the missing permissions, and then overriding
        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
        //                                          int[] grantResults)
        // to handle the case where the user grants the permission. See the documentation
        // for ActivityCompat#requestPermissions for more details.
        return
    }
        mFusedLocationClient.lastLocation
        .addOnSuccessListener { location: Location? ->
            l = location
        }

    val geocoder = Geocoder(context, Locale.getDefault())

    val textState = remember { mutableStateOf(TextFieldValue()) }
    var lines = postList
    val welcomeText = remember { mutableStateOf("") }

    val k = geocoder.getFromLocation(41.6946, -8.83016, 1)

    welcomeText.value = k[0]!!.locality.toString()

    if (visible) {
        Scaffold(
            bottomBar = { Box(modifier = Modifier.padding(10.dp)) {

    Column(modifier = Modifier.fillMaxWidth()) {

        Row(modifier = Modifier.fillMaxWidth()) {
            TextField(value = textState.value, onValueChange = {textState.value = it},
                modifier = Modifier
                    .offset(0.dp, (-80).dp))
            Button(onClick = {
                {/**TODO**/}
            },
                Modifier
                    .offset(0.dp, (-80).dp)
                    .width(60.dp)
                    .background(color = Artip_pink))

            {
                Text(text = ">",
                    fontSize = 30.sp,
                    color = Color.Black,
                    modifier = Modifier
                        .size(40.dp)
                        .padding(0.dp)
                )
            }
        }
    }
            } }
        ) {
            Column(modifier = Modifier
                .fillMaxWidth()) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(Artip_pink)
                    .padding(20.dp)) {
                    Text(text = "Bem-vindo a " + welcomeText.value)
                }
                Spacer(Modifier.size(10.dp))
                LazyColumn(){
                    items(lines){ l ->
                        Feed_Entry(pd = l)
                    }
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview4() {
    val ls:MutableList<PostData> = Arrays.asList(PostData("JoelDoe", "Hello Everyone!"))
    ArtipTheme {
        FeedCompo(true, ls)
    }
}

@Composable
fun InputMessage(pl: MutableList<PostData>) {


    
}