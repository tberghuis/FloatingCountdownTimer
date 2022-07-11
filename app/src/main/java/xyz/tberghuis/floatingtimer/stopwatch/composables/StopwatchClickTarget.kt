package xyz.tberghuis.floatingtimer.stopwatch.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import xyz.tberghuis.floatingtimer.logd


@Composable
fun StopwatchClickTarget() {
  Box(
    modifier = Modifier
      .background(Color.Red)
      .clickable {
        onClickStopwatchClickTarget()
      }

  ) {
    Text("hello click target")
  }

}


// todo move into another file
fun onClickStopwatchClickTarget(){
  logd("click target")
}
