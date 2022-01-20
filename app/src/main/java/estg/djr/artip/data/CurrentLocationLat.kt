package estg.djr.artip.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CurrentLocationLat(private val context: Context) {

    companion object {
        private val Context.dataStoree: DataStore<Preferences> by preferencesDataStore("lat_preference")
        val LAT = doublePreferencesKey("latPreference")
    }


    // Receber dados do radius
    val getLatPref: Flow<Double?> = context.dataStoree.data
        .map { preferences ->
            preferences[LAT] ?: -14.2
        }

    suspend fun saveLat(value: Double) {
        context.dataStoree.edit { preferences ->
            preferences[LAT] = value
        }
    }


}