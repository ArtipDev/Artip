package estg.djr.artip

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.LocationServices
import com.google.android.libraries.maps.CameraUpdateFactory
import com.google.android.libraries.maps.MapView
import com.google.android.libraries.maps.model.LatLng
import com.google.android.libraries.maps.model.Marker
import com.google.android.libraries.maps.model.MarkerOptions
import com.google.android.libraries.maps.model.PolylineOptions
import com.google.maps.android.ktx.awaitMap
import estg.djr.artip.ui.theme.ArtipTheme
import estg.djr.artip.ui.theme.Artip_pink
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class Map : ComponentActivity() {

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
fun GoogleMap(visible : Boolean, localizacao: LatLng) {
    if(visible) {
        val mapView = rememberMapViewWithLifeCycle()

        val name = remember { mutableStateOf("") }
        val v = remember { mutableStateOf(false)}

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Box() {
                AndroidView(
                    {mapView}
                ) { mapView ->
                    CoroutineScope(Dispatchers.Main).launch {
                        val map = mapView.awaitMap()
                        map.uiSettings.isZoomControlsEnabled = false

                        val pickUp = LatLng(41.6946, -8.83016) //Viana
                        val destination = LatLng(41.15, -8.61024) //Bangalore
                        val Localizacao = localizacao

                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(Localizacao, 6f))

                        val markerOptions =  MarkerOptions()
                            .title("Viana do Castelo")
                            .position(pickUp)
                        map.addMarker(markerOptions)

                        val markerOptionsLocalizacao = MarkerOptions()
                            .title("Você")
                            .position(localizacao)
                        map.addMarker(markerOptionsLocalizacao)

                        val markerOptionsDestination = MarkerOptions()
                            .title("Porto")
                            .position(destination)
                        map.addMarker(markerOptionsDestination)

                        map.setOnMarkerClickListener(com.google.android.libraries.maps.GoogleMap.OnMarkerClickListener {
                            Log.d("MARKER__", it.title.toString())
                            v.value = true
                            name.value = it.title.toString()
                            true
                        })
                    }
            }
            ArtistPopInfo(name = name.value, v.value)
            }
        }
    }
}


@Composable
fun ArtistPopInfo(name: String, visible: Boolean) {
    if(visible){
        Column(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(Artip_pink)
        ) {
            Row(Modifier.padding(10.dp),
            Arrangement.SpaceEvenly) {
                Image(painterResource(R.drawable.profile_icon), contentDescription = "",
                    modifier = Modifier
                        .size(90.dp)
                        .padding(10.dp)
                        .clip(shape = CircleShape))
                Text(text = name)
            }
            Spacer(modifier = Modifier.size(0.dp))
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(10.dp)) {
                Button(onClick = { /*TODO*/ },
                    Modifier.weight(1f)) {
                    Text(text = "Click me")
                }
                Spacer(modifier = Modifier.size(10.dp))
                Button(onClick = { /*TODO*/ },
                    Modifier.weight(1f)) {
                    Text(text = "No me...")
                }
            }
        }
    }
}

@Preview
@Composable
fun forPreview(){
    ArtistPopInfo(name = "Someone", visible = true)
}


@Composable
fun rememberMapViewWithLifeCycle(): MapView {
    val context = LocalContext.current
    val mapView = remember {
        MapView(context).apply {
            id = com.google.maps.android.ktx.R.id.map_frame
        }
    }
    val lifeCycleObserver = rememberMapLifecycleObserver(mapView)
    val lifeCycle = LocalLifecycleOwner.current.lifecycle
    DisposableEffect(lifeCycle) {
        lifeCycle.addObserver(lifeCycleObserver)
        onDispose {
            lifeCycle.removeObserver(lifeCycleObserver)
        }
    }

    return mapView
}

@Composable
fun rememberMapLifecycleObserver(mapView: MapView): LifecycleEventObserver =
    remember(mapView) {
        LifecycleEventObserver { _, event ->
            when(event) {
                Lifecycle.Event.ON_CREATE -> mapView.onCreate(Bundle())
                Lifecycle.Event.ON_START -> mapView.onStart()
                Lifecycle.Event.ON_RESUME -> mapView.onResume()
                Lifecycle.Event.ON_PAUSE -> mapView.onPause()
                Lifecycle.Event.ON_STOP -> mapView.onStop()
                Lifecycle.Event.ON_DESTROY -> mapView.onDestroy()
                else -> throw IllegalStateException()
            }
        }
    }