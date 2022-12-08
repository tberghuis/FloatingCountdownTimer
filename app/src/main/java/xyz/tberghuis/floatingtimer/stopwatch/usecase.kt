package xyz.tberghuis.floatingtimer.stopwatch

import android.app.Service
import xyz.tberghuis.floatingtimer.logd

// todo move to a usecase class, inject with hilt/dagger
fun stopwatchExit(stopwatchOverlayComponent: StopwatchOverlayComponent, stopwatchState: StopwatchState) {
  logd("stopwatchExit")
  // fix, this makes timertask cancel
  stopwatchState.resetStopwatchState()
  stopwatchOverlayComponent.removeOverlays()
  // todo, here decides to stopForground if no overlays
  //  stopwatchServiceHolder.exitStopwatch()
  (stopwatchOverlayComponent.context as Service).stopSelf()
}