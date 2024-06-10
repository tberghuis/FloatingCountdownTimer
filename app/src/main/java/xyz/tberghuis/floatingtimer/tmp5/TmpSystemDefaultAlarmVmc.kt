package xyz.tberghuis.floatingtimer.tmp5

import android.app.Application
import android.media.RingtoneManager
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class TmpSystemDefaultAlarmVmc(val application: Application) {
  var systemDefaultRingtoneUri by mutableStateOf<String?>(null)
  var systemDefaultRingtoneTitle by mutableStateOf("")

  init {
    getDefaultRingtone()
  }

  private fun getDefaultRingtone() {
    val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
    systemDefaultRingtoneUri = uri?.toString()
    systemDefaultRingtoneTitle = uri?.let {
      RingtoneManager.getRingtone(application, uri).getTitle(application)
    } ?: ""
  }
}