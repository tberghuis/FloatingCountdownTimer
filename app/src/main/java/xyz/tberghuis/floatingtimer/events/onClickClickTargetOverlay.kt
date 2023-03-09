package xyz.tberghuis.floatingtimer.events

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import xyz.tberghuis.floatingtimer.countdown.OverlayStateHolder
import xyz.tberghuis.floatingtimer.REQUEST_CODE_PENDING_ALARM
import xyz.tberghuis.floatingtimer.countdown.TimerStateFinished
import xyz.tberghuis.floatingtimer.countdown.TimerStatePaused
import xyz.tberghuis.floatingtimer.countdown.TimerStateRunning
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.receivers.AlarmReceiver

fun onClickClickTargetOverlay(context: Context, player: MediaPlayer, countdownOverlayState: OverlayStateHolder) {
  logd("onTimerClick")
  when (countdownOverlayState.timerState.value) {
    is TimerStatePaused -> {
      logd("todo: run the timer")

      // launch intent for alarm
      val intent = Intent(context, AlarmReceiver::class.java)
      intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
      // todo do i need to save pendingAlarm to state???
      countdownOverlayState.pendingAlarm = PendingIntent.getBroadcast(
        context.applicationContext,
        REQUEST_CODE_PENDING_ALARM,
        intent,
        PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
      )
      val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
      alarmManager.setAlarmClock(
        AlarmManager.AlarmClockInfo(
          System.currentTimeMillis() + (countdownOverlayState.countdownSeconds * 1000),
          countdownOverlayState.pendingAlarm
        ),
        countdownOverlayState.pendingAlarm
      )
      countdownOverlayState.timerState.value = TimerStateRunning
    }
    is TimerStateRunning -> {
      countdownOverlayState.timerState.value = TimerStatePaused
      countdownOverlayState.pendingAlarm?.cancel()
    }
    is TimerStateFinished -> {
      player.pause()
      countdownOverlayState.resetTimerState(countdownOverlayState.durationSeconds)
    }
  }
}