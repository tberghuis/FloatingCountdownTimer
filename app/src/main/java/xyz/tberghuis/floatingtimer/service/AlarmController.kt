package xyz.tberghuis.floatingtimer.service

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.REQUEST_CODE_PENDING_ALARM
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.receivers.AlarmReceiver
import xyz.tberghuis.floatingtimer.service.countdown.TimerStateFinished
import xyz.tberghuis.floatingtimer.service.countdown.TimerStatePaused
import xyz.tberghuis.floatingtimer.service.countdown.TimerStateRunning

class AlarmController(val service: FloatingService) {

  val player: MediaPlayer

  private var pendingAlarm: PendingIntent? = null

  init {
    val alarmSound: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
    player = MediaPlayer.create(service, alarmSound)
    player.isLooping = true

    watchState()
  }

  private fun watchState() {
    val context = service
    val countdownState = service.state.countdownState

    CoroutineScope(Dispatchers.Default).launch {
      countdownState.timerState.collectLatest {
        when (it) {
          is TimerStateFinished -> {
            logd("does the player start")
            player.start()
            pendingAlarm?.cancel()
          }

          TimerStateRunning -> {
            // set alarm
            logd("todo: run the timer")
            val intent = Intent(context, AlarmReceiver::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            // todo do i need to save pendingAlarm to state???
            pendingAlarm = PendingIntent.getBroadcast(
              context.applicationContext,
              REQUEST_CODE_PENDING_ALARM,
              intent,
              PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.setAlarmClock(
              AlarmManager.AlarmClockInfo(
                System.currentTimeMillis() + (countdownState.countdownSeconds * 1000), pendingAlarm
              ), pendingAlarm
            )
          }
          TimerStatePaused -> {
            pendingAlarm?.cancel()
            if (player.isPlaying) {
              player.pause()
            }
          }
        }
      }
    }
  }
}