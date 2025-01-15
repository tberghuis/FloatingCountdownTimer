package xyz.tberghuis.floatingtimer.viewmodels

import android.app.Application
import android.media.Ringtone
import android.os.Build
import androidx.core.net.toUri
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.util.uriToRingtone

class RingtonePreviewVmc(val application: Application) {
  private var ringtone: Ringtone? = null
  private var lastRingtoneUri: String? = null

  fun ringtoneClick(uri: String) {
    logd("ringtoneClick $uri")
    if (ringtone?.isPlaying == true && lastRingtoneUri == uri) {
      ringtone?.stop()
      return
    }
    ringtone?.stop()
    ringtone = uriToRingtone(application, uri.toUri())?.apply {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        // this didn't work on emulator alarm
        // for alarms such as: "Krypton"
        isLooping = false
      }
    }
    lastRingtoneUri = uri
    ringtone?.play()
  }

  fun onCleared() {
    ringtone?.stop()
  }
}