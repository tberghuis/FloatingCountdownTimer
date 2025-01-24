package xyz.tberghuis.floatingtimer.tmp4

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
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
    modifier = Modifier.padding(8.dp / 2), // arcWidth / 2
    background = {
      CountdownProgressArc(
        timeLeftFraction,
        8.dp,
        Color.Blue,
        false
      )
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