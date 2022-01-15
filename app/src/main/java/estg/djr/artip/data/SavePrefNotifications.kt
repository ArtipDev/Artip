package estg.djr.artip.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SavePrefNotifications(private val context: Context) {

    companion object {
        private val Context.dataStoree: DataStore<Preferences> by preferencesDataStore("notificationsPref")
        val NOTPREF = booleanPreferencesKey("notification_pref")
    }

    // Receber dados do radius
    val getNotPref: Flow<Boolean?> = context.dataStoree.data
        .map { preferences ->
            preferences[NOTPREF] ?: false
        }

    suspend fun saveNotifications(value: Boolean) {
        context.dataStoree.edit { preferences ->
            preferences[NOTPREF] = value
        }
    }


}