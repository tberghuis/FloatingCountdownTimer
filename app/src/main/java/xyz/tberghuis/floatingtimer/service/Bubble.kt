package xyz.tberghuis.floatingtimer.service

import android.util.Log
import xyz.tberghuis.floatingtimer.ARC_WIDTH_NO_SCALE
import xyz.tberghuis.floatingtimer.TIMER_FONT_SIZE_NO_SCALE
import xyz.tberghuis.floatingtimer.TIMER_SIZE_NO_SCALE
import xyz.tberghuis.floatingtimer.tmp2.BubbleProperties

//  todo : BubbleProperties
abstract class Bubble(
  private val service: FloatingService,
  bubbleSizeScaleFactor: Float
) : BubbleProperties {
  final override val bubbleSizeDp = TIMER_SIZE_NO_SCALE * (bubbleSizeScaleFactor + 1)
  val bubbleSizePx: Int = (bubbleSizeDp.value * service.resources.displayMetrics.density).toInt()
  override val arcWidth = ARC_WIDTH_NO_SCALE * (0.9f * bubbleSizeScaleFactor + 1)
  override val fontSize = TIMER_FONT_SIZE_NO_SCALE * (1.2 * bubbleSizeScaleFactor + 1)
  val viewHolder = TimerViewHolder(service, bubbleSizePx)

  open fun exit() {
    try {
      service.overlayController.windowManager.removeView(viewHolder.view)
    } catch (e: IllegalArgumentException) {
      Log.e("Bubble", "IllegalArgumentException $e")
    }
  }

  abstract fun reset()
  abstract fun onTap()
}