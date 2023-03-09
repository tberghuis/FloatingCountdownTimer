package xyz.tberghuis.floatingtimer.events

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import xyz.tberghuis.floatingtimer.REQUEST_CODE_PENDING_ALARM
import xyz.tberghuis.floatingtimer.countdown.CountdownState
import xyz.tberghuis.floatingtimer.countdown.TimerStateFinished
import xyz.tberghuis.floatingtimer.countdown.TimerStatePaused
import xyz.tberghuis.floatingtimer.countdown.TimerStateRunning
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.receivers.AlarmReceiver

fun onClickClickTargetOverlay(
  player: MediaPlayer, countdownState: CountdownState
) {
  logd("onTimerClick")
  when (countdownState.timerState.value) {
    is TimerStatePaused -> {
      countdownState.timerState.value = TimerStateRunning
    }
    is TimerStateRunning -> {
      countdownState.timerState.value = TimerStatePaused
    }
    is TimerStateFinished -> {
      player.pause()
      countdownState.resetTimerState(countdownState.durationSeconds)
    }
  }
}