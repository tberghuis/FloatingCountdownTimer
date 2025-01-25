package xyz.tberghuis.floatingtimer.tmp4

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import xyz.tberghuis.floatingtimer.composables.TimeDisplay
import xyz.tberghuis.floatingtimer.service.countdown.CountdownProgressArc

@Composable
fun TmpCountdownCircleView(
  bubbleProperties: TmpBubbleProperties,
  timeLeftFraction: Float,
  countdownSeconds: Int,
  isPaused: Boolean,
  isBackgroundTransparent: Boolean
) {
  SquareBackground(
    modifier = Modifier.padding(bubbleProperties.arcWidth / 2),
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
    Box(
      Modifier
        .padding(5.dp),
      contentAlignment = Alignment.Center, // todo test if needed
    ) {
      TimeDisplay(countdownSeconds, bubbleProperties.fontSize, isBackgroundTransparent)
    }
  }
}

//@Preview
//@Composable
//fun CountdownCircleViewPreview() {
//  TmpCountdownCircleView(.5f, 3600 + 59)
//}