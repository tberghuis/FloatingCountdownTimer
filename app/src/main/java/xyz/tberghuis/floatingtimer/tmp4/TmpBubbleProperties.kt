package xyz.tberghuis.floatingtimer.tmp4

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import xyz.tberghuis.floatingtimer.ARC_WIDTH_NO_SCALE
import xyz.tberghuis.floatingtimer.TIMER_FONT_SIZE_NO_SCALE
import xyz.tberghuis.floatingtimer.TIMER_PADDING_NO_SCALE

interface TmpBubbleProperties {
  val arcWidth: Dp
  val fontSize: TextUnit
  val haloColor: Color
  val timerShape: String
  val label: String?
  val isBackgroundTransparent: Boolean
  val paddingTimerDisplay: Dp

  companion object {
    fun calcArcWidth(scaleFactor: Float) = ARC_WIDTH_NO_SCALE * (0.9f * scaleFactor + 1)
    fun calcFontSize(scaleFactor: Float) = TIMER_FONT_SIZE_NO_SCALE * (1.2 * scaleFactor + 1)
    fun calcTimerDisplayPadding(scaleFactor: Float) = TIMER_PADDING_NO_SCALE * (.9f * scaleFactor + 1)
  }
}