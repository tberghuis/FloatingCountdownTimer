package xyz.tberghuis.floatingtimer.tmp5

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import xyz.tberghuis.floatingtimer.composables.CountdownProgressLine

@Composable
fun TmpTimerRectView(
  arcWidth: Dp,
  haloColor: Color,
  timeLeftFraction: Float,
) {
  Box {
    Box {
      Column {
        Text("hello rect view")
        CountdownProgressLine(
          timeLeftFraction,
          arcWidth,
          haloColor
        )
      }
    }
  }
}