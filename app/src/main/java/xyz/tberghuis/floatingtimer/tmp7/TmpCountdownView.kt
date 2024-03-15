package xyz.tberghuis.floatingtimer.tmp7

import androidx.compose.runtime.Composable
import xyz.tberghuis.floatingtimer.service.BubbleProperties

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
      TimerRectView(
        isPaused = isPaused,
        widthDp = bubbleProperties.widthDp,
        heightDp = bubbleProperties.heightDp,
        arcWidth = bubbleProperties.arcWidth,
        haloColor = bubbleProperties.haloColor,
        timeElapsed = countdownSeconds,
        timeLeftFraction = timeLeftFraction,
        fontSize = bubbleProperties.fontSize,
      )
    }

    else -> {
      throw RuntimeException("invalid timer shape")
    }
  }

}
