package xyz.tberghuis.floatingtimer.stopwatch

import xyz.tberghuis.floatingtimer.logd

// doitwrong
// todo move to a usecase class, inject with hilt/dagger
fun stopwatchExit() {
  logd("stopwatchExit")
  stopwatchServiceHolder.stopwatchOverlayComponent.removeOverlays()

  // todo, here decides to stopForground if no overlays
//  stopwatchServiceHolder.exitStopwatch()


  // doitwrong
  stopwatchServiceHolder.stopSelf()
}