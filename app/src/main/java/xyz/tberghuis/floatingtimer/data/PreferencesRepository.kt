package xyz.tberghuis.floatingtimer.data

import android.content.Context
import android.media.RingtoneManager
import androidx.compose.ui.graphics.Color
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import xyz.tberghuis.floatingtimer.BuildConfig
import xyz.tberghuis.floatingtimer.DEFAULT_HALO_COLOR
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
    preferences[booleanPreferencesKey("halo_colour_purchased")] ?: BuildConfig.DEFAULT_PURCHASED
  }

  suspend fun updateHaloColourPurchased(purchased: Boolean) {
    dataStore.edit { preferences ->
      preferences[booleanPreferencesKey("halo_colour_purchased")] = purchased
    }
  }

  val haloColourFlow: Flow<Color> = dataStore.data.map { preferences ->
    val haloColourString = preferences[stringPreferencesKey("halo_colour")]
    val haloColor = if (haloColourString == null)
      DEFAULT_HALO_COLOR
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

  val alarmRingtoneUriFlow: Flow<String?> = dataStore.data.map { preferences ->
    var uri = preferences[stringPreferencesKey("alarm_ringtone_uri")]
    if (uri == null) {
      uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)?.toString()
    }
    uri
  }.distinctUntilChanged()

  suspend fun updateAlarmRingtoneUri(uri: String) {
    dataStore.edit { preferences ->
      preferences[stringPreferencesKey("alarm_ringtone_uri")] = uri
    }
  }

  val loopingFlow: Flow<Boolean> = dataStore.data.map { preferences ->
    preferences[booleanPreferencesKey("looping")] ?: true
  }

  suspend fun updateLooping(looping: Boolean) {
    dataStore.edit { preferences ->
      preferences[booleanPreferencesKey("looping")] = looping
    }
  }


  companion object {
    @Volatile
    private var instance: PreferencesRepository? = null
    fun getInstance(context: Context) =
      instance ?: synchronized(this) {
        instance ?: PreferencesRepository(context.dataStore).also { instance = it }
      }
  }


}

//fun Context.providePreferencesRepository(): PreferencesRepository {
//  return PreferencesRepository.getInstance(this)
//}

val Context.preferencesRepository : PreferencesRepository
  get() = PreferencesRepository.getInstance(this)
