package estg.djr.artip.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CurrentLocationLong(private val context: Context) {

    companion object {
        private val Context.dataStoree: DataStore<Preferences> by preferencesDataStore("long_preference")
        val LONG = doublePreferencesKey("longPreference")
    }


    // Receber dados do radius
    val getLongPref: Flow<Double?> = context.dataStoree.data
        .map { preferences ->
            preferences[LONG] ?: -14.2
        }

    suspend fun saveLong(value: Double) {
        context.dataStoree.edit { preferences ->
            preferences[LONG] = value
        }
    }


}