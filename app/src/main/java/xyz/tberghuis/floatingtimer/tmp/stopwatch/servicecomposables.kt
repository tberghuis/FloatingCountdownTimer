package xyz.tberghuis.floatingtimer.tmp.stopwatch

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import xyz.tberghuis.floatingtimer.common.TimeDisplay
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.stopwatch.stopwatchServiceHolder
import java.util.*
import kotlin.concurrent.timerTask
import xyz.tberghuis.floatingtimer.stopwatch.StopwatchStateHolder as state

// todo inject ExitStopwatch usecase
@Composable
fun StopwatchServiceOverlay(
//  exit: () -> Unit
) {

  Column(
    modifier = Modifier.background(Color.LightGray),
    verticalArrangement = Arrangement.Center
  ) {
    Text("hello stopwatch overlay")
    Row {
      StartPauseButton()
      StopwatchTimeDisplay()
    }
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


@Composable
fun StopwatchTimeDisplay() {
  TimeDisplay(state.timeElapsed.value)
}

@Composable
fun StartPauseButton() {
  Button(onClick = {
    logd("start pause")

    when (state.running) {
      false -> {
        state.running = true
        Timer().scheduleAtFixedRate(timerTask {
          logd("timertask")
          if (state.running) {
            state.timeElapsed.value++
          } else {
            cancel()
          }
        }, 1000, 1000)
      }
      true -> {
        state.running = false
      }
    }


  }) {
    Text("start pause")
  }

}
