package estg.djr.artip.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SavePrefRadius(private val context: Context) {

    companion object {
        private val Context.dataStoree: DataStore<Preferences> by preferencesDataStore("radiusPref")
        val RADIUSPREF = stringPreferencesKey("radius_pred")
    }

    // Receber dados do radius
    val getRadiusPref: Flow<String?> = context.dataStoree.data
        .map { preferences ->
            preferences[RADIUSPREF] ?: "5"
        }

    suspend fun saveRadius(value: String) {
        context.dataStoree.edit { preferences ->
            preferences[RADIUSPREF] = value
        }
    }

}