package xyz.tberghuis.floatingtimer.events

import android.media.MediaPlayer
import xyz.tberghuis.floatingtimer.countdown.CountdownStateVFDVDFV
import xyz.tberghuis.floatingtimer.countdown.TimerStateFinished
import xyz.tberghuis.floatingtimer.countdown.TimerStatePaused
import xyz.tberghuis.floatingtimer.countdown.TimerStateRunning
import xyz.tberghuis.floatingtimer.logd

fun onClickClickTargetOverlay(
  player: MediaPlayer, countdownState: CountdownStateVFDVDFV
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