package estg.djr.artip.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GotoUser(private val context: Context) {

    companion object {
        private val Context.dataStoree: DataStore<Preferences> by preferencesDataStore("goto_user")
        val GOTOUSER = stringPreferencesKey("gotoUser")
    }


    // Receber dados do radius
    val getGotoUser: Flow<String?> = context.dataStoree.data
        .map { preferences ->
            preferences[GOTOUSER] ?: ""
        }

    suspend fun setGotoUser(value: String) {
        context.dataStoree.edit { preferences ->
            preferences[GOTOUSER] = value
        }
    }


}