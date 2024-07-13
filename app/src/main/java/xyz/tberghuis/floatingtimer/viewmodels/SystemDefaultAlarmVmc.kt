package xyz.tberghuis.floatingtimer.viewmodels

import android.app.Application
import android.media.RingtoneManager
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class SystemDefaultAlarmVmc(val application: Application) {
  var systemDefaultRingtoneUri by mutableStateOf<String?>(null)
  var systemDefaultRingtoneTitle by mutableStateOf("")

  init {
    getDefaultRingtone()
  }

  private fun getDefaultRingtone() {
    val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
    systemDefaultRingtoneUri = uri?.toString()
    systemDefaultRingtoneTitle = uri?.let {
      try {
        RingtoneManager.getRingtone(application, uri).getTitle(application)
      } catch (e: SecurityException) {
        Log.e("SystemDefaultAlarmVmc", "SecurityException $e")
        null
      } catch (e: NullPointerException) {
        Log.e("SystemDefaultAlarmVmc", "NullPointerException $e")
        null
      }
    } ?: ""
  }
}