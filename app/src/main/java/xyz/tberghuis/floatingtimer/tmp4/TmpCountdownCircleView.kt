package xyz.tberghuis.floatingtimer.tmp4

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import xyz.tberghuis.floatingtimer.service.BubbleProperties
import xyz.tberghuis.floatingtimer.service.countdown.CountdownProgressArc

@Composable
fun TmpCountdownCircleView(
  bubbleProperties: BubbleProperties,
  timeLeftFraction: Float,
  countdownSeconds: Int,
  isPaused: Boolean,
  isBackgroundTransparent: Boolean
) {
  SquareBackground(
    modifier = Modifier.padding(bubbleProperties.arcWidth / 2), // arcWidth / 2
    background = {
      Box {
        CountdownProgressArc(
          timeLeftFraction,
          bubbleProperties.arcWidth,
          bubbleProperties.haloColor,
          isBackgroundTransparent
        )
        if (isPaused) {
          Icon(
            Icons.Filled.PlayArrow,
            contentDescription = "paused",
            modifier = Modifier.fillMaxSize(),
            tint = Color.LightGray
          )
        }
      }
    },
  ) {
    Box(Modifier.padding(5.dp)) {
//      TimeDisplay(countdownSeconds)
      // todo
      Text("time goes here")
    }
  }
}

//@Preview
//@Composable
//fun CountdownCircleViewPreview() {
//  TmpCountdownCircleView(.5f, 3600 + 59)
//}