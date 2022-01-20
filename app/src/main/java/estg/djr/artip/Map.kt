package estg.djr.artip

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.android.gms.location.*
import com.google.android.libraries.maps.CameraUpdateFactory
import com.google.android.libraries.maps.MapView
import com.google.android.libraries.maps.model.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.maps.android.ktx.awaitMap
import com.skydoves.landscapist.glide.GlideImage
import estg.djr.artip.ui.theme.ArtipTheme
import estg.djr.artip.ui.theme.Artip_pink
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.google.android.libraries.maps.model.BitmapDescriptorFactory

import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Offset
import androidx.core.app.ActivityCompat

import com.google.android.libraries.maps.model.BitmapDescriptor


private var db = FirebaseFirestore.getInstance();
private var canAcessToFirebase : Boolean = false

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
fun GoogleMap(visible : Boolean, Localizacao: LatLng) {

    val context = LocalContext.current

    Log.d("lat__", Localizacao.toString())

    if(visible) {
        val mapView = rememberMapViewWithLifeCycle()

        val name = remember { mutableStateOf("") }
        val markerDocUid = remember { mutableStateOf("") }
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
                        map.uiSettings.isMyLocationButtonEnabled = true
                        if (ActivityCompat.checkSelfPermission(
                                context,
                                Manifest.permission.ACCESS_FINE_LOCATION
                            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                context,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            return@launch
                        }
                        map.isMyLocationEnabled=true



                        map.setMapStyle((MapStyleOptions.loadRawResourceStyle(context, R.raw.mapstyle)))

                        val pickUp = LatLng(Localizacao.latitude,Localizacao.longitude)
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(pickUp, 10f))

                        val docRef = db.collection("markers")
                        docRef.addSnapshotListener { snapshot, e ->
                            if (e != null) {
                                return@addSnapshotListener
                            }


                        if (snapshot != null ) {
                            map.clear()
                            val documents = snapshot.documents

                            for (document in documents) {

                                val geoPoint = document.getGeoPoint("loc")
                                val name = document.get("name")
                                val userPhotoUrl = document.get("urlPhoto")


                                val lat = geoPoint!!.latitude
                                val lng = geoPoint!!.longitude

                                val geopoints = LatLng(lat, lng)

                                val markerOptionsDestination = MarkerOptions()
                                    .title(name.toString())
                                    .position(geopoints)
                                    .icon(bitmapDescriptorFromVector(context = context, R.drawable.artlogo))

                                map.addMarker(markerOptionsDestination).tag = userPhotoUrl

                            }


                            map.setOnMarkerClickListener(com.google.android.libraries.maps.GoogleMap.OnMarkerClickListener {
                                Log.d("MARKER__", it.title.toString())
                                v.value = true
                                name.value = it.title.toString()
                                markerDocUid.value = it.tag.toString()
                                true
                            })

                            map.setOnMapClickListener ( com.google.android.libraries.maps.GoogleMap.OnMapClickListener {
                                v.value = false
                            })


                        } else {
                            Log.d("FIREBASETESTE", "Current data: null")
                        }
                    }

                    }
            }
            ArtistPopInfo(name = name.value, v.value, markerDocUid.value)
                Box(Modifier.fillMaxSize(), Alignment.BottomCenter) {
                    Button(onClick = { /*TODO*/ }, Modifier.background(color = Artip_pink).offset(x = 0.dp, y = -80.dp)) {
                        Text(text = "Start!", Modifier.padding(10.dp))
                    }
                }
            }


            



        }
    }
}

private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
    return ContextCompat.getDrawable(context, vectorResId)?.run {
        setBounds(0, 0, 150, 150)
        val bitmap = Bitmap.createBitmap(150, 150, Bitmap.Config.ARGB_8888)
        draw(Canvas(bitmap))
        BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}
@Composable
fun ArtistPopInfo(name: String, visible: Boolean, markerUidDoc:String) {


    if(visible){
        Column(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(Artip_pink)
        ) {
            Row(Modifier.padding(10.dp),


            Arrangement.SpaceEvenly) {

                 GlideImage(
                    imageModel = markerUidDoc,
                    modifier = Modifier
                        .size(90.dp)
                        .padding(10.dp)
                        .clip(shape = CircleShape),
                    error = ImageBitmap.imageResource(R.drawable.profile_icon)
                )
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
    ArtistPopInfo(name = "Someone", visible = true, "https://lh3.googleusercontent.com/a/AATXAJyEBAfnbxsRDXsmqzTETt6A7vhrzVqIQIA9yAMx=s96-c")
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