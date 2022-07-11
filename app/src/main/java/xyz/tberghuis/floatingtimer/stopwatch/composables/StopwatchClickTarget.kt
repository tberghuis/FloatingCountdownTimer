package xyz.tberghuis.floatingtimer.stopwatch.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.stopwatch.stopwatchState
import java.util.*
import kotlin.concurrent.timerTask
import kotlin.math.roundToInt

@Composable
fun StopwatchClickTarget() {

  Box(
    modifier = Modifier
      .background(Color.Red)
      .pointerInput(Unit) {
        detectDragGestures(onDragStart = {
          logd("clicktarget onDragStart")

        },
          onDrag = { change, dragAmount ->

          },
          onDragEnd = {
            logd("onDragEnd")

          }
        )
      }
      .clickable {
        onClickStopwatchClickTarget()
      }

  ) {
    Text("hello click target")
  }

}


// todo move into another file
fun onClickStopwatchClickTarget() {
  logd("click target start pause")
  when (stopwatchState.running.value) {
    false -> {
      stopwatchState.running.value = true
      Timer().scheduleAtFixedRate(timerTask {
        logd("timertask")
        if (stopwatchState.running.value) {
          stopwatchState.timeElapsed.value++
        } else {
          cancel()
        }
      }, 1000, 1000)
    }
    true -> {
      stopwatchState.running.value = false
    }
  }
}