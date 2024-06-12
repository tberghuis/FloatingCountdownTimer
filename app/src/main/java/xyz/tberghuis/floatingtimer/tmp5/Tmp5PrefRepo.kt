package xyz.tberghuis.floatingtimer.tmp5

import android.media.RingtoneManager
import android.net.Uri
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.net.URI

class Tmp5PrefRepo(
  private val dataStore: DataStore<Preferences>
) {
  val alarmRingtoneUriFlow: Flow<String?> = dataStore.data.map { preferences ->
    var uri = preferences[stringPreferencesKey("alarm_ringtone_uri")]
    if (uri == null) {
      uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)?.toString()
    }
    uri
  }

  suspend fun updateAlarmRingtoneUri(uri: String) {
    dataStore.edit { preferences ->
      preferences[stringPreferencesKey("alarm_ringtone_uri")] = uri
    }
  }
}