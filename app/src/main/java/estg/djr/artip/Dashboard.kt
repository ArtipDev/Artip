package estg.djr.artip

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import com.google.android.libraries.maps.GoogleMap
import com.google.android.libraries.maps.model.LatLng
import estg.djr.artip.data.CurrentLocationLat
import estg.djr.artip.data.CurrentLocationLong
import estg.djr.artip.dataclasses.PostData
import estg.djr.artip.ui.theme.ArtipTheme
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*

class Dashboard : ComponentActivity() {
    val nv = Navbar()

    private lateinit var lastLocation: Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    //added to implement location periodic updates
    private lateinit var locationCallback: LocationCallback
    private lateinit var locationRequest: LocationRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtipTheme {
                MainPage(nv)
            }
        }

        createLocationRequest()

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)
                lastLocation = p0.lastLocation
                var loc = LatLng(lastLocation.latitude, lastLocation.longitude)
                val latData = CurrentLocationLat(context = this@Dashboard)
                val longData = CurrentLocationLong(context = this@Dashboard)
                runBlocking {
                    latData.saveLat(loc.latitude)
                    longData.saveLong(loc.longitude)

                    Log.d("****AMARO", loc.latitude.toString())
                    Log.d("****AMARO", loc.longitude.toString())
                }
                Log.d("****AMARO", "Out of ")
            }
        }
    }

    private fun createLocationRequest() {
        locationRequest = LocationRequest.create().apply {
            interval = 1500
            fastestInterval = 1500
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

    override fun onPause() {
        super.onPause()
        fusedLocationClient.removeLocationUpdates(locationCallback)
        Log.d("****AMARO","onPause - RemoveLocationUpdates")
    }

    override fun onResume() {
        super.onResume()
        startLocationUpdates()
        Log.d("****AMARO","onResume - startLocationUpdates")
    }

    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                44
            )
            return
        }
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    @Composable
    fun MainPage(tabChange: Navbar = Navbar()) {

        val list: List<PostData> =
            Arrays.asList(PostData("123123","JohnDoe", "Um doid três", "https://lh3.googleusercontent.com/a/AATXAJyEBAfnbxsRDXsmqzTETt6A7vhrzVqIQIA9yAMx=s96-c"),
                PostData("123123","JohnDoe", "Um doid três", "https://lh3.googleusercontent.com/a/AATXAJyEBAfnbxsRDXsmqzTETt6A7vhrzVqIQIA9yAMx=s96-c"),
                PostData("123123","JohnDoe", "Um doid três", "https://lh3.googleusercontent.com/a/AATXAJyEBAfnbxsRDXsmqzTETt6A7vhrzVqIQIA9yAMx=s96-c"))


        var vi: Boolean = true
        val currentTab : Int = tabChange.tab.value
        Scaffold(
            bottomBar = { BottomNavigationBar(nv) },
        ) {
            when(currentTab) {
                0 -> {
                    GoogleMap(visible = true)
                    ProfileCampo(false, false)
                    SettingsCompo(visible = false)
                    FeedCompo(visible = false,list)
                }
                1 -> {
                    GoogleMap(visible = false)
                    ProfileCampo(visible = false, false)
                    SettingsCompo(visible = false)
                    FeedCompo(true,list)
                }
                2 -> {
                FeedCompo(visible = false,list)
                ProfileCampo(true, false)
                GoogleMap(visible = false)
                SettingsCompo(visible = false)
                }
                3 -> {
                    FeedCompo(visible = false,list)
                    ProfileCampo(true, myProfile = true)
                    GoogleMap(visible = false)
                    SettingsCompo(visible = false)

                }
                4 -> {
                    SettingsCompo(visible = true)
                    FeedCompo(visible = false, list)
                    GoogleMap(visible = false)
                    ProfileCampo(visible = false, false)
                }

            }

        }
    }
}