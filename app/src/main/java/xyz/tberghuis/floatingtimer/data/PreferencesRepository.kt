package xyz.tberghuis.floatingtimer.data

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import xyz.tberghuis.floatingtimer.logd

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

  val haloColourPurchasedFlow: Flow<Boolean> = dataStore.data.map { preferences ->
    preferences[booleanPreferencesKey("halo_colour_purchased")] ?: false
  }

  suspend fun updateHaloColourPurchased(purchased: Boolean) {
    dataStore.edit { preferences ->
      preferences[booleanPreferencesKey("halo_colour_purchased")] = purchased
    }
  }

  val haloColourFlow: Flow<Color> = dataStore.data.map { preferences ->
    val haloColourString = preferences[stringPreferencesKey("halo_colour")]
    val haloColor = if (haloColourString == null)
    // this is same as MaterialTheme.colorScheme.primary outside of FloatingTimerTheme
      Color(
        red = 103,
        green = 80,
        blue = 164
      )
    else
      Color(haloColourString.toULong())
    logd("haloColourFlow halocolor $haloColor")
    haloColor
  }

  suspend fun updateHaloColour(color: Color) {
    logd("updateHaloColour")
    val haloColourString = color.value.toString()
    dataStore.edit { preferences ->
      preferences[stringPreferencesKey("halo_colour")] = haloColourString
    }
  }

  // todo test this
  suspend fun resetHaloColour() {
    dataStore.edit { preferences ->
      preferences.remove(stringPreferencesKey("halo_colour"))
    }
  }

  val bubbleScaleFlow: Flow<Float> = dataStore.data.map { preferences ->
    preferences[floatPreferencesKey("bubble_scale")] ?: 0f
  }

  suspend fun updateBubbleScale(scale: Float) {
    dataStore.edit { preferences ->
      preferences[floatPreferencesKey("bubble_scale")] = scale
    }
  }

  suspend fun resetBubbleScale() {
    dataStore.edit { preferences ->
      preferences.remove(floatPreferencesKey("bubble_scale"))
    }
  }
}