package xyz.tberghuis.floatingtimer.data

import android.content.Context
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(
  name = "user_preferences",
)

class PreferencesRepository(private val dataStore: DataStore<Preferences>) {
  val vibrationFlow: Flow<Boolean> = dataStore.data.map { preferences ->
    preferences[booleanPreferencesKey("vibration")] ?: true
  }

  suspend fun updateVibration(vibration: Boolean) {
    dataStore.edit { preferences ->
      preferences[booleanPreferencesKey("vibration")] = vibration
    }
  }

  val soundFlow: Flow<Boolean> = dataStore.data.map { preferences ->
    preferences[booleanPreferencesKey("sound")] ?: true
  }

  suspend fun updateSound(sound: Boolean) {
    dataStore.edit { preferences ->
      preferences[booleanPreferencesKey("sound")] = sound
    }
  }

  suspend fun checkFirstRun(): Boolean {
    return dataStore.data.map { preferences ->
      preferences[booleanPreferencesKey("first_run")] ?: true
    }.first()
  }

  suspend fun setFirstRunFalse() {
    dataStore.edit { preferences ->
      preferences[booleanPreferencesKey("first_run")] = false
    }
  }


  val haloColourPurchasedFlow: Flow<Boolean> = dataStore.data.map { preferences ->
    preferences[booleanPreferencesKey("halo_colour_purchased")] ?: false
  }

  suspend fun updateHaloColourPurchased(purchased: Boolean) {
    dataStore.edit { preferences ->
      preferences[booleanPreferencesKey("halo_colour_purchased")] = purchased
    }
  }

  val haloColourFlow: Flow<Long?> = dataStore.data.map { preferences ->
    preferences[longPreferencesKey("halo_colour")]
  }

  suspend fun updateHaloColour(argb: Long) {
    dataStore.edit { preferences ->
      preferences[longPreferencesKey("halo_colour")] = argb
    }
  }

  // todo test this
  suspend fun resetHaloColour() {
    dataStore.edit { preferences ->
      preferences.remove(longPreferencesKey("halo_colour"))
    }
  }

}
