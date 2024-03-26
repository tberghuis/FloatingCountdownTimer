package xyz.tberghuis.floatingtimer.tmp5

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import xyz.tberghuis.floatingtimer.ARC_WIDTH_NO_SCALE
import xyz.tberghuis.floatingtimer.TIMER_FONT_SIZE_NO_SCALE
import xyz.tberghuis.floatingtimer.composables.TimerLabelView

@Preview(showBackground = true)
@Composable
fun TmpTimerLabelView(
) {
  val isPaused = true
  val arcWidth = ARC_WIDTH_NO_SCALE * (0.9f * 1f + 1)
  val haloColor = Color.Green
  val timeElapsed = 59
  val timeLeftFraction = 1f
  val fontSize = TIMER_FONT_SIZE_NO_SCALE * (1.2 * 1f + 1)
  val label = "label - "

  TimerLabelView(
    isPaused,
    arcWidth,
    haloColor,
    timeElapsed,
    timeLeftFraction,
    fontSize,
    label
  )
}

