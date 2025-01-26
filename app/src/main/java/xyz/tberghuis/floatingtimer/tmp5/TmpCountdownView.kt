package xyz.tberghuis.floatingtimer.tmp5

import androidx.compose.runtime.Composable
import xyz.tberghuis.floatingtimer.service.countdown.CountdownCircleView
import xyz.tberghuis.floatingtimer.tmp4.TmpBubbleProperties as BubbleProperties

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
        isPaused = isPaused,
        bubbleProperties.isBackgroundTransparent
      )
    }

    "label", "rectangle" -> {
      // this is redundant, unless bad data in DB
      val label = if (bubbleProperties.timerShape == "label") bubbleProperties.label else null
      TmpTimerRectView2(
        isPaused,
        bubbleProperties.arcWidth,
        bubbleProperties.haloColor,
        countdownSeconds,
        timeLeftFraction,
        bubbleProperties.fontSize,
        label,
        bubbleProperties.isBackgroundTransparent,
      )
    }

    else -> {
      throw RuntimeException("invalid timer shape")
    }
  }
}