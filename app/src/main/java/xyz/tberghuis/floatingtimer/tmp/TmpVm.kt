package xyz.tberghuis.floatingtimer.tmp

import android.app.Application
import android.media.Ringtone
import android.media.RingtoneManager
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import xyz.tberghuis.floatingtimer.logd


// why no compiler error "application" ??? was happening in NoteBoat
class TmpVm(private val application: Application) : AndroidViewModel(application) {

  // only access from Main+immediate ???
  var ringtone: Ringtone? = null

  fun initialiseRingtone() {
    val notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
    ringtone = RingtoneManager.getRingtone(application, notification)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
      ringtone?.isLooping = true
    }
  }

  fun playRingtone() {
    ringtone?.play()
  }
}