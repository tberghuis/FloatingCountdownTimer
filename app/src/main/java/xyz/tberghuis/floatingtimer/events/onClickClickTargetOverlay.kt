package xyz.tberghuis.floatingtimer.events

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import xyz.tberghuis.floatingtimer.OverlayStateHolder.countdownSeconds
import xyz.tberghuis.floatingtimer.OverlayStateHolder.durationSeconds
import xyz.tberghuis.floatingtimer.OverlayStateHolder.pendingAlarm
import xyz.tberghuis.floatingtimer.OverlayStateHolder.timerState
import xyz.tberghuis.floatingtimer.REQUEST_CODE_PENDING_ALARM
import xyz.tberghuis.floatingtimer.TimerStateFinished
import xyz.tberghuis.floatingtimer.TimerStatePaused
import xyz.tberghuis.floatingtimer.TimerStateRunning
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.receivers.AlarmReceiver
import xyz.tberghuis.floatingtimer.resetTimerState

fun onClickClickTargetOverlay(context: Context, player: MediaPlayer) {
  logd("onTimerClick")
  when (timerState.value) {
    is TimerStatePaused -> {
      logd("todo: run the timer")

      // launch intent for alarm
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
          System.currentTimeMillis() + (countdownSeconds * 1000),
          pendingAlarm
        ),
        pendingAlarm
      )
      timerState.value = TimerStateRunning
    }
    is TimerStateRunning -> {
      timerState.value = TimerStatePaused
      pendingAlarm?.cancel()
    }
    is TimerStateFinished -> {
      player.pause()
      resetTimerState(durationSeconds)
    }
  }
}