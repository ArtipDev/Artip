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
import android.location.Geocoder
import android.location.Location
import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalDensity
import androidx.core.app.ActivityCompat

import com.google.android.libraries.maps.model.BitmapDescriptor
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.GeoPoint
import estg.djr.artip.data.*
import kotlinx.coroutines.runBlocking
import java.util.*


private var db = FirebaseFirestore.getInstance();
private var mAuth = FirebaseAuth.getInstance()
private var canAcessToFirebase : Boolean = false
private var currentMarker: String = ""
private var isPerforming: Boolean = false
private var markerUserId: String = ""

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
fun GoogleMap(visible : Boolean, tab : Navbar) {

    Log.d("LATLONG_", "123123123123")

    var btnAtuacao = remember { mutableStateOf("Começar atuação!")}

    val context = LocalContext.current

    val dataLatPref = CurrentLocationLat(context)
    val savedLat = dataLatPref.getLatPref.collectAsState(initial = 0.00)
    val dataLongPref = CurrentLocationLong(context)
    val savedLong = dataLongPref.getLongPref.collectAsState(initial = 0.00)

    val dataUserType = SavePrefUserType(context)
    val savedUserType = dataUserType.getUserTypePref.collectAsState(initial = false)

    val dataRadius = SavePrefRadius(context)
    val savedRadius = dataRadius.getRadiusPref.collectAsState(initial = "1")

    if(visible) {
        val mapView = rememberMapViewWithLifeCycle()

        val name = remember { mutableStateOf("") }
        val userPhoto = remember { mutableStateOf("") }

        val v = remember { mutableStateOf(false)}
        val vMarkerPop = remember { mutableStateOf(false)}

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

                        val pickUp = LatLng(savedLat.value!!, savedLong.value!!)
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(pickUp, 10f))

                        val docRef = db.collection("markers")
                        docRef.addSnapshotListener { snapshot, e ->
                            if (e != null) {
                                return@addSnapshotListener
                            }


                        if (snapshot != null ) {
                            map.clear()
                            val documents = snapshot.documents

                            if(documents.size>0) {
                                for (document in documents) {

                                    val geoPoint = document.getGeoPoint("loc")
                                    val name = document.get("name")
                                    val userPhotoUrl = document.get("urlPhoto")


                                    val lat = geoPoint!!.latitude
                                    val lng = geoPoint!!.longitude


                                    val geopoints = LatLng(lat, lng)

                                    val results = FloatArray(1)
                                    val distanceBetween = Location.distanceBetween(
                                        lat,
                                        lng,
                                        savedLat.value!!,
                                        savedLong.value!!,
                                        results
                                        )


                                    if((results[0].toDouble()/1000) < savedRadius.value!!.toDouble()) {
                                        val markerOptionsDestination = MarkerOptions()
                                            .title(name.toString())
                                            .position(geopoints)
                                            .icon(bitmapDescriptorFromVector(context = context, R.drawable.artlogo))

                                        map.addMarker(markerOptionsDestination).tag = userPhotoUrl
                                    }
                                }
                            }

                            map.setOnMarkerClickListener(com.google.android.libraries.maps.GoogleMap.OnMarkerClickListener {
                                Log.d("MARKER__", it.title.toString())
                                v.value = true
                                name.value = it.title.toString()
                                userPhoto.value = it.tag.toString()
                                markerUserId = it.id
                                true
                            })

                            map.setOnMapClickListener ( com.google.android.libraries.maps.GoogleMap.OnMapClickListener {
                                v.value = false
                                vMarkerPop.value = false
                            })


                        } else {
                            Log.d("FIREBASETESTE", "Current data: null")
                        }

                    }
                    }
            }
            ArtistPopInfo(name = name.value, v.value, userPhoto.value, tab)
            ArtistPopCreateMarker(vMarkerPop.value)


                if(savedUserType.value!!) {
                    Box(Modifier.fillMaxSize(), Alignment.BottomCenter) {
                        Button(onClick = {
                            if(isPerforming) {
                                removeMarker(context = context)
                                btnAtuacao.value = "Começar atuação!"
                            } else {
                                vMarkerPop.value = true
                                btnAtuacao.value = "Parar atuação!"
                            }
                        },
                            Modifier
                                .background(color = Artip_pink)
                                .offset(x = 0.dp, y = -80.dp)) {
                            Text(text = btnAtuacao.value, Modifier.padding(10.dp))
                        }
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
fun ArtistPopInfo(name: String, visible: Boolean, markerUidDoc:String, nav: Navbar) {

    val context = LocalContext.current

    val gu = GotoUser(context)



        val density = LocalDensity.current
        AnimatedVisibility(
            visible,
            enter = slideInVertically {
                with(density) { -40.dp.roundToPx() }
            } + expandVertically(
                expandFrom = Alignment.Top
            ) + fadeIn(
                initialAlpha = 0.3f
            ),
            exit = slideOutVertically() + shrinkVertically() + fadeOut()
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(Artip_pink)
            ) {


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
                        Button(onClick = {
                            runBlocking {
                                gu.setGotoUser(markerUserId)
                            }
                            nav.tab.value = 5
                        },
                            Modifier.weight(1f)) {
                            Text(text = "Profile")
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
    }



fun insertNewMarker(context: Context, lat: Double, long : Double, streetName : String) {

    val currentUser = mAuth.currentUser
    val timestamp = FieldValue.serverTimestamp();
    val location = GeoPoint(lat,long)

    val marker = hashMapOf(
        "createdAt" to timestamp,
        "artistname" to currentUser?.displayName.toString(),
        "loc" to location,
        "name" to streetName.toString(),
        "urlPhoto" to currentUser?.photoUrl.toString(),
        "currentUserId" to currentUser?.uid
    )

    db.collection("markers")
        .add(marker)
        .addOnSuccessListener { documentReference ->
            Log.d("DATA____", "ADDED " + documentReference.id)
            currentMarker = documentReference.id
            Toast.makeText(context, "Começo da atuação!", Toast.LENGTH_SHORT).show()
        }
        .addOnFailureListener { e -> Log.w("INSERIR", "Error adding document", e) }
}

fun removeMarker(context: Context) {
    val currentUser = mAuth.currentUser

    db.collection("markers")
        .document(currentMarker).delete()
        .addOnSuccessListener {
            Toast.makeText(context, "Atuação finalizada!", Toast.LENGTH_SHORT).show()
        }
        .addOnFailureListener { e -> Log.w("INSERIR", "Error adding document", e) }
}


@Composable
fun ArtistPopCreateMarker(visible:Boolean) {

    var context = LocalContext.current
    var currentUser = mAuth.currentUser

    val dataLatPref = CurrentLocationLat(context)
    val savedLat = dataLatPref.getLatPref.collectAsState(initial = 0.00)
    val dataLongPref = CurrentLocationLong(context)
    val savedLong = dataLongPref.getLongPref.collectAsState(initial = 0.00)
    val geocoder = Geocoder(context, Locale.getDefault())


    val geoReverse = geocoder.getFromLocation(savedLat.value!!,savedLong.value!! ,1)



        val density = LocalDensity.current
        AnimatedVisibility(
            visible,
            enter = slideInVertically {
                with(density) { -40.dp.roundToPx() }
            } + expandVertically(
                expandFrom = Alignment.Top
            ) + fadeIn(
                initialAlpha = 0.3f
            ),
            exit = slideOutVertically() + shrinkVertically() + fadeOut()
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(Artip_pink)
            ) {
                Row(Modifier.padding(10.dp),
                    Arrangement.SpaceEvenly) {
                    GlideImage(
                        imageModel = currentUser?.photoUrl,
                        modifier = Modifier
                            .size(90.dp)
                            .padding(10.dp)
                            .clip(shape = CircleShape),
                        error = ImageBitmap.imageResource(R.drawable.profile_icon)
                    )
                    Text(text = geoReverse[0].adminArea.toString())
                }
                Spacer(modifier = Modifier.size(0.dp))
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(10.dp)) {
                    Button(onClick = {
                        insertNewMarker(context = context,  savedLat.value!!.toDouble(), savedLong.value!!.toDouble(), geoReverse[0].adminArea.toString())
                        isPerforming = true
                    },
                        Modifier.weight(1f)) {
                        Text(text = "Começar Atuação!")
                    }

                }

        }
    }


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