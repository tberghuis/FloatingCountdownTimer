package xyz.tberghuis.floatingtimer.service

import android.util.Log
import xyz.tberghuis.floatingtimer.tmp2.BubbleProperties

//  todo : BubbleProperties
abstract class Bubble(
  private val service: FloatingService,
  bubbleSizeScaleFactor: Float
) : BubbleProperties {
  final override val bubbleSizeDp = BubbleProperties.calcBubbleSizeDp(bubbleSizeScaleFactor)
  val bubbleSizePx: Int = (bubbleSizeDp.value * service.resources.displayMetrics.density).toInt()
  override val arcWidth = BubbleProperties.calcArcWidth(bubbleSizeScaleFactor)
  override val fontSize = BubbleProperties.calcFontSize(bubbleSizeScaleFactor)
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