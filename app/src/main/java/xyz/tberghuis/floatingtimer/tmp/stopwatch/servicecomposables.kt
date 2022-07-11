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
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.stopwatch.stopwatchServiceHolder
import xyz.tberghuis.floatingtimer.stopwatch.stopwatchState
import java.util.*
import kotlin.concurrent.timerTask

// TODO delete this file

// todo inject ExitStopwatch usecase
@Composable
fun StopwatchServiceOverlay(
//  exit: () -> Unit
) {

  Column(
    modifier = Modifier.background(Color.LightGray),
    verticalArrangement = Arrangement.Center
  ) {

//    StopwatchOverlay()
    Button(onClick = {
      logd("exit composable")
//      exit()
    }) {
      Text("exit")
    }
  }
}

