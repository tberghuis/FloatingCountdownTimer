package xyz.tberghuis.floatingtimer.tmp7

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.MutableStateFlow
import xyz.tberghuis.floatingtimer.common.TimeDisplay
import xyz.tberghuis.floatingtimer.service.stopwatch.Stopwatch
import xyz.tberghuis.floatingtimer.tmp4.CountdownProgressLine

@Composable
fun StopwatchRectView(
  stopwatch: Stopwatch
) {
  StopwatchRectView(
    isRunningStateFlow = stopwatch.isRunningStateFlow,
    widthDp = stopwatch.widthDp,
    heightDp = stopwatch.heightDp,
    arcWidth = stopwatch.arcWidth,
    haloColor = stopwatch.haloColor,
    timeElapsed = stopwatch.timeElapsed.intValue,
    fontSize = stopwatch.fontSize,
  )
}

@Composable
fun StopwatchRectView(
  isRunningStateFlow: MutableStateFlow<Boolean>?,
  widthDp: Dp,
  heightDp: Dp,
  arcWidth: Dp,
  haloColor: Color,
  timeElapsed: Int,
  fontSize: TextUnit,
) {
  val isRunning = isRunningStateFlow?.collectAsState()?.value

  Box(
    modifier = Modifier
      .size(widthDp, heightDp)
      .padding(5.dp)
      .graphicsLayer(
        shadowElevation = with(LocalDensity.current) { 5.dp.toPx() },
        shape = RoundedCornerShape(10.dp),
        clip = true
      ),
    contentAlignment = Alignment.Center,
  ) {

    Box(
      modifier = Modifier
        .background(Color.White)
        .fillMaxSize(),
      contentAlignment = Alignment.Center,
    ) {

      if (isRunning == false) {
        Icon(
          Icons.Filled.PlayArrow,
          contentDescription = "paused",
          modifier = Modifier.fillMaxSize(),
          tint = Color.LightGray
        )
      }

      Column(
        modifier = Modifier
          .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
      ) {
        TimeDisplay(timeElapsed, fontSize)
        Box(
          modifier = Modifier.padding(
            start = 5.dp,
            end = 5.dp,
            bottom = 5.dp
          ),
          contentAlignment = Alignment.TopStart,
        ) {
          CountdownProgressLine(
            1f,
            arcWidth,
            haloColor
          )
        }
      }
    }
  }
}