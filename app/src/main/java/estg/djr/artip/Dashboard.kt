package estg.djr.artip

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
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
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.android.libraries.maps.model.LatLng
import estg.djr.artip.ui.theme.ArtipTheme

class Dashboard : ComponentActivity() {
    val nv = Navbar()
    private val viewModel by viewModels<estg.djr.artip.MapView>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getLocationPermission()
        setContent {
            ArtipTheme {
                val newDestinationSelected = viewModel.isNewLocationSelected.observeAsState(false)

                val localizacao = if (newDestinationSelected.value) LatLng(viewModel.selectedLat.value, viewModel.selectedLng.value) else  LatLng(0.0, 0.0)

                MainPage(nv, localizacao)
            }
        }
    }
    private fun getLocationPermission(){
        if (ContextCompat.checkSelfPermission(this.applicationContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            viewModel.permissionGrand(true)
            getDeviceLocation()

        }else{
            Log.d("Exception", "Permission not granted")
        }
    }
    private  fun getDeviceLocation(){
        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        try {
            if (viewModel.locationPermissionGranted.value == true){
                val locationResult = fusedLocationProviderClient.lastLocation

                locationResult.addOnCompleteListener {
                        task ->
                    if (task.isSuccessful){
                        val lastKnownLocation = task.result

                        if (lastKnownLocation != null){
                            viewModel.currentUserGeoCord(
                                LatLng(
                                    lastKnownLocation.altitude,
                                    lastKnownLocation.longitude
                                )
                            )
                        }
                    }else{
                        Log.d("Exception","Não foi possivel obter a localização.")
                    }
                }

            }

        }catch (e: SecurityException){
            Log.d("*Exception*", "Exception: ${e.message.toString()}")
        }
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