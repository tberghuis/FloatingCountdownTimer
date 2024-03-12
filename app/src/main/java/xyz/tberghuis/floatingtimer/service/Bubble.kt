package xyz.tberghuis.floatingtimer.service

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import xyz.tberghuis.floatingtimer.ARC_WIDTH_NO_SCALE
import xyz.tberghuis.floatingtimer.TIMER_FONT_SIZE_NO_SCALE
import xyz.tberghuis.floatingtimer.TIMER_SIZE_NO_SCALE

//interface BubbleProperties {
//  val bubbleSizeDp: Dp
//  val arcWidth: Dp
//  val fontSize: TextUnit
//  val haloColor: Color
//
//  companion object {
//    fun calcBubbleSizeDp(scaleFactor: Float) = TIMER_SIZE_NO_SCALE * (scaleFactor + 1)
//    fun calcArcWidth(scaleFactor: Float) = ARC_WIDTH_NO_SCALE * (0.9f * scaleFactor + 1)
//    fun calcFontSize(scaleFactor: Float) = TIMER_FONT_SIZE_NO_SCALE * (1.2 * scaleFactor + 1)
//  }
//}

//abstract class Bubble(
//  private val service: FloatingService,
//  bubbleSizeScaleFactor: Float,
//  override val haloColor: Color
//) : BubbleProperties {
//  final override val bubbleSizeDp = BubbleProperties.calcBubbleSizeDp(bubbleSizeScaleFactor)
//  val bubbleSizePx: Int = (bubbleSizeDp.value * service.resources.displayMetrics.density).toInt()
//  override val arcWidth = BubbleProperties.calcArcWidth(bubbleSizeScaleFactor)
//  override val fontSize = BubbleProperties.calcFontSize(bubbleSizeScaleFactor)
//  val viewHolder = TimerViewHolder(service, bubbleSizePx)
//
//  open fun exit() {
//    try {
//      service.overlayController.windowManager.removeView(viewHolder.view)
//    } catch (e: IllegalArgumentException) {
//      Log.e("Bubble", "IllegalArgumentException $e")
//    }
//  }
//
//  abstract fun reset()
//  abstract fun onTap()
//}