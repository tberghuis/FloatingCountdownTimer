package xyz.tberghuis.floatingtimer.tmp5

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class Tmp5PrefRepo(private val dataStore: DataStore<Preferences>) {
  val alarmRingtoneUriFlow: Flow<String> = dataStore.data.map { preferences ->
    // todo get default alarm uri
    // if null
    // use "" empty string to represent none.
    preferences[stringPreferencesKey("alarm_ringtone_uri")]
      ?: "content://settings/system/alarm_alert"
  }

  suspend fun updateAlarmRingtoneUri(uri: String) {
    dataStore.edit { preferences ->
      preferences[stringPreferencesKey("alarm_ringtone_uri")] = uri
    }
  }
}