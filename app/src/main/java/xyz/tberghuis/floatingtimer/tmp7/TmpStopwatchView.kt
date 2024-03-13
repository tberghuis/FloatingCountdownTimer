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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import xyz.tberghuis.floatingtimer.common.TimeDisplay
import xyz.tberghuis.floatingtimer.service.stopwatch.Stopwatch
import xyz.tberghuis.floatingtimer.tmp4.CountdownProgressLine

@Composable
fun TmpStopwatchView(
  stopwatch: Stopwatch
) {
  Box(
    modifier = Modifier
      .size(stopwatch.widthDp, stopwatch.heightDp)
      .padding(5.dp),
    contentAlignment = Alignment.Center,
  ) {


    Box(
      modifier = Modifier
        .graphicsLayer(
          shadowElevation = with(LocalDensity.current) { 5.dp.toPx() },
          shape = RoundedCornerShape(10.dp),
          clip = false
        )
        .clip(RoundedCornerShape(10.dp)),
    ) {



      Column(
        modifier = Modifier
          .background(Color.White)
          .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
      ) {

        TimeDisplay(59, stopwatch.fontSize)

        Box(
          modifier = Modifier.padding(
            start = 5.dp,
            end = 5.dp,
            bottom = 5.dp
          ),
          contentAlignment = Alignment.TopStart,
        ) {
          CountdownProgressLine(
            0.5f,
            stopwatch.arcWidth,
            stopwatch.haloColor
          )
        }
      }

    }


  }
}
