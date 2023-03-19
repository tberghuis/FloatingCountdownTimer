package xyz.tberghuis.floatingtimer.service

import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.countdown.TimerStateFinished
import xyz.tberghuis.floatingtimer.logd

class AlarmController(val service: FloatingService) {

  val player: MediaPlayer

  init {
    val alarmSound: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
    player = MediaPlayer.create(service, alarmSound)
    player.isLooping = true

    watchState()
  }

  private fun watchState() {
    CoroutineScope(Dispatchers.Default).launch {
      service.state.countdownState.timerState.collectLatest {
        when (it) {
          is TimerStateFinished -> {
            logd("does the player start")
            player.start()
          }
          else -> {}
        }
      }
    }
  }


}