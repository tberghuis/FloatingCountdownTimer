package xyz.tberghuis.floatingtimer.viewmodels

import android.app.Application
import android.media.RingtoneManager
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.util.uriToRingtone

class SystemDefaultAlarmVmc(val application: Application, val scope: CoroutineScope) {
  var systemDefaultRingtoneUri by mutableStateOf<String?>(null)
  var systemDefaultRingtoneTitle by mutableStateOf("")

  init {
    getDefaultRingtone()
  }

  private fun getDefaultRingtone() {
    scope.launch {
      val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
      systemDefaultRingtoneUri = uri?.toString()
      systemDefaultRingtoneTitle = uri?.let {
        try {
          uriToRingtone(application, uri)?.getTitle(application)
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
}