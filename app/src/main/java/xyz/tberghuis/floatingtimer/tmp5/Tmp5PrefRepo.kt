package xyz.tberghuis.floatingtimer.tmp5

import android.app.Application
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class Tmp5PrefRepo(
  private val application: Application,
  private val dataStore: DataStore<Preferences>
) {
  private val alarmRingtoneUriFlow: Flow<String> = dataStore.data.map { preferences ->
    // todo get default alarm uri
    // if null
    // use "" empty string to represent none.
    preferences[stringPreferencesKey("alarm_ringtone_uri")]
      ?: "content://settings/system/alarm_alert"
  }

  val alarmRingtoneFlow: Flow<Ringtone?> = alarmRingtoneUriFlow.map {
    RingtoneManager.getRingtone(application, Uri.parse(it))
  }

  suspend fun updateAlarmRingtoneUri(uri: String) {
    dataStore.edit { preferences ->
      preferences[stringPreferencesKey("alarm_ringtone_uri")] = uri
    }
  }
}