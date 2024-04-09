package xyz.tberghuis.floatingtimer.service.stopwatch

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import kotlinx.coroutines.flow.MutableStateFlow
import xyz.tberghuis.floatingtimer.tmp4.TmpTimeDisplay

@Composable
fun StopwatchCircleView(
  // when isRunningStateFlow null, showing preview in saved timers card
  isRunningStateFlow: MutableStateFlow<Boolean>?,
  bubbleSizeDp: Dp,
  arcWidth: Dp,
  haloColor: Color,
  timeElapsed: Int,
  fontSize: TextUnit,
  isBackgroundTransparent: Boolean
) {
  val isRunning = isRunningStateFlow?.collectAsState()?.value
  Box(
    modifier = Modifier
      .size(bubbleSizeDp)
      .padding(arcWidth / 2),
    contentAlignment = Alignment.Center
  ) {
    StopwatchBorderArc(isRunningStateFlow, arcWidth, haloColor, isBackgroundTransparent)
    if (isRunning == false) {
      Icon(
        Icons.Filled.PlayArrow,
        contentDescription = "paused",
        modifier = Modifier.fillMaxSize(),
        tint = Color.LightGray
      )
    }
//    TimeDisplay(timeElapsed, fontSize)
    TmpTimeDisplay(timeElapsed, fontSize, isBackgroundTransparent)
  }
}