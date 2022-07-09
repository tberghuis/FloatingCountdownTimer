package xyz.tberghuis.floatingtimer.tmp.stopwatch

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.stopwatch.stopwatchServiceHolder
import xyz.tberghuis.floatingtimer.stopwatch.StopwatchStateHolder as state

// todo inject ExitStopwatch usecase
@Composable
fun StopwatchServiceOverlay(
//  exit: () -> Unit
) {

  Column(verticalArrangement = Arrangement.Center) {
    Text("hello stopwatch overlay")
    Text(state.willitblend)
    Button(onClick = {
      logd("exit composable")
      exit()
    }) {
      Text("exit")
    }
  }
}

// doitwrong
// todo move to a usecase class, inject with hilt/dagger
fun exit() {
  logd("exit fn")
  val view = stopwatchServiceHolder.stopwatchOverlayComponent.fullscreenOverlay.view
  stopwatchServiceHolder.stopwatchOverlayComponent.windowManager.removeView(view)

  // todo, here decides to stopForground if no overlays
//  stopwatchServiceHolder.exitStopwatch()
}
