package xyz.tberghuis.floatingtimer.tmp2

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import xyz.tberghuis.floatingtimer.ARC_WIDTH_NO_SCALE
import xyz.tberghuis.floatingtimer.TIMER_FONT_SIZE_NO_SCALE
import xyz.tberghuis.floatingtimer.TIMER_SIZE_NO_SCALE

interface BubbleProperties {
  val bubbleSizeDp: Dp
  val arcWidth: Dp
  val fontSize: TextUnit

  // todo haloColour
//  val haloColour: Color

  companion object {
    fun calcBubbleSizeDp(scaleFactor: Float) = TIMER_SIZE_NO_SCALE * (scaleFactor + 1)
    fun calcArcWidth(scaleFactor: Float) = ARC_WIDTH_NO_SCALE * (0.9f * scaleFactor + 1)
    fun calcFontSize(scaleFactor: Float) = TIMER_FONT_SIZE_NO_SCALE * (1.2 * scaleFactor + 1)
  }
}