package xyz.tberghuis.floatingtimer.viewmodels

import android.app.Application
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import xyz.tberghuis.floatingtimer.logd

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
    ringtone = RingtoneManager.getRingtone(application, Uri.parse(uri))
    lastRingtoneUri = uri
    ringtone?.play()
  }
}