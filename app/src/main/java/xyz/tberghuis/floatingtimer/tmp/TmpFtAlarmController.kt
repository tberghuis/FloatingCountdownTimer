package xyz.tberghuis.floatingtimer.tmp

import android.app.Application
import android.media.Ringtone
import android.media.RingtoneManager
import android.os.Build
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.service.countdown.Countdown

class TmpFtAlarmController(
  application: Application,
  scope: CoroutineScope
) {
  private var ringtone: Ringtone? = null
  private val finishedCountdowns = MutableStateFlow(setOf<Countdown>())

  init {
    val notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
    ringtone = RingtoneManager.getRingtone(application, notification)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
      ringtone?.isLooping = true
    }
    scope.launch(Main.immediate) {
      finishedCountdowns.collect {
        when (it.size) {
          0 -> {
            if (ringtone?.isPlaying == true) {
              ringtone?.stop()
            }
          }

          1 -> {
            if (ringtone?.isPlaying == false) {
              ringtone?.play()
            }
          }
        }
      }
    }
  }

  fun startAlarm(c: Countdown) {
    finishedCountdowns.value += setOf(c)
  }

  fun stopAlarm(c: Countdown) {
    finishedCountdowns.value -= setOf(c)
  }
}