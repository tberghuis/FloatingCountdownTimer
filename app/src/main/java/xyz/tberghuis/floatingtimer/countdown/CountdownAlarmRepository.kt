package xyz.tberghuis.floatingtimer.countdown

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// todo use in the future if complains about lost alarms on service process death
// or device restarts
class CountdownAlarmRepository(private val dataStore: DataStore<Preferences>) {
  val alarmTimeFlow: Flow<Long?> = dataStore.data.map { preferences ->
    preferences[longPreferencesKey("countdown_alarm_time")]
  }
  suspend fun updateAlarmTime(alarmTime: Long) {
    dataStore.edit { preferences ->
      preferences[longPreferencesKey("countdown_alarm_time")] = alarmTime
    }
  }
}