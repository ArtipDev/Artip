package estg.djr.artip

import android.Manifest
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
import estg.djr.artip.ui.theme.ArtipTheme

class Dashboard : ComponentActivity() {
    val nv = Navbar()

    private lateinit var lastLocation: Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    //added to implement location periodic updates
    private lateinit var locationCallback: LocationCallback
    private lateinit var locationRequest: LocationRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createLocationRequest()

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)
                lastLocation = p0.lastLocation
                var loc = LatLng(lastLocation.latitude, lastLocation.longitude)
                setContent {
                    ArtipTheme {
                        MainPage(nv, loc)
                        Log.d("Lets go!", ""+loc)
                    }
                }
            }
        }
    }

    private fun createLocationRequest() {
        locationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 10000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

    override fun onPause() {
        super.onPause()
        fusedLocationClient.removeLocationUpdates(locationCallback)
        Log.d("****Joao","onPause - RemoveLocationUpdates")
    }

    override fun onResume() {
        super.onResume()
        startLocationUpdates()
        Log.d("****Joao","onResume - startLocationUpdates")
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
    fun MainPage(tabChange: Navbar = Navbar(), localizacao: LatLng) {
        var vi: Boolean = true
        val currentTab : Int = tabChange.tab.value
        Scaffold(
            bottomBar = { BottomNavigationBar(nv) },
        ) {
            when(currentTab) {
                0 -> {
                    GoogleMap(visible = true, localizacao)
                    ProfileCompo(false)
                    FeedCompo(visible = false)
                }
                1 -> {
                    GoogleMap(visible = false, localizacao)
                    ProfileCompo(visible = false)
                    //FeedCompo(true)
                }
                2 -> {
                    FeedCompo(visible = false)
                    ProfileCompo(true)
                    GoogleMap(visible = false, localizacao)

                }

            }

        }
    }
}