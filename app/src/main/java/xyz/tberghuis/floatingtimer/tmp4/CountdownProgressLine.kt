package xyz.tberghuis.floatingtimer.tmp4

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

@Composable
fun CountdownProgressLine(timeLeftFraction: Float, arcWidth: Dp, haloColor: Color) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .height(arcWidth)
      .background(haloColor.copy(alpha = .1f))
  ) {
    Box(
      modifier = Modifier
        .fillMaxWidth(timeLeftFraction)
        .height(arcWidth)
        .background(haloColor)
    )
  }
}