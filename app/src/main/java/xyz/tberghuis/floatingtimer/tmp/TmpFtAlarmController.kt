package xyz.tberghuis.floatingtimer.tmp

import android.app.Application
import android.media.Ringtone
import android.media.RingtoneManager
import android.os.Build
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.service.FloatingService
import xyz.tberghuis.floatingtimer.service.countdown.Countdown

class TmpFtAlarmController(
  floatingService: FloatingService
) {
  private var ringtone: Ringtone? = null
  private val finishedCountdowns = MutableStateFlow(setOf<TmpCountdown>())

  init {
    val notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
    ringtone = RingtoneManager.getRingtone(floatingService, notification)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
      ringtone?.isLooping = true
    }
    floatingService.scope.launch(Main.immediate) {
      try {
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
      } finally {
        // scope is cancelled
        ringtone?.stop()
      }
    }
  }

  fun startAlarm(c: TmpCountdown) {
//    finishedCountdowns.value += setOf(c)
    finishedCountdowns.value += c
  }

  fun stopAlarm(c: TmpCountdown) {
    finishedCountdowns.value -= c
  }
}