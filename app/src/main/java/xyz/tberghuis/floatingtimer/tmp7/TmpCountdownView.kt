package xyz.tberghuis.floatingtimer.tmp7

import androidx.compose.runtime.Composable
import xyz.tberghuis.floatingtimer.tmp5.BubbleProperties

@Composable
fun TmpCountdownView(
  bubbleProperties: BubbleProperties,
  timeLeftFraction: Float,
  countdownSeconds: Int,
  isPaused: Boolean
) {

  when (bubbleProperties.timerShape) {
    "circle" -> {
      CountdownCircleView(
        bubbleProperties = bubbleProperties,
        timeLeftFraction = timeLeftFraction,
        countdownSeconds = countdownSeconds,
        isPaused = isPaused
      )
    }

    "rectangle" -> {


//       TimerRectView(
//  isRunningStateFlow=!isPaused,
//  widthDp: Dp,
//  heightDp: Dp,
//  arcWidth: Dp,
//  haloColor: Color,
//  timeElapsed: Int,
//  timeLeftFraction: Float,
//  fontSize: TextUnit,
//)
    }
    else -> TODO()
  }

}
