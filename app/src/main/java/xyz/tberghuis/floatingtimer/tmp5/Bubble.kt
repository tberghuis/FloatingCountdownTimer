package xyz.tberghuis.floatingtimer.tmp5

import android.util.Log
import androidx.compose.ui.graphics.Color
import xyz.tberghuis.floatingtimer.service.FloatingService
import kotlin.math.roundToInt

abstract class Bubble(
  private val service: FloatingService,
  bubbleSizeScaleFactor: Float,
  override val haloColor: Color,
  timerShape: String
) : BubbleProperties {
  final override val widthDp = BubbleProperties.calcWidthDp(bubbleSizeScaleFactor)

  // todo when
  final override val heightDp = when (timerShape) {
    "circle" -> {
      BubbleProperties.calcWidthDp(bubbleSizeScaleFactor)
    }

    "rectangle" -> {
      BubbleProperties.calcRectHeightDp(bubbleSizeScaleFactor)
    }
    // todo
    else -> {
      TODO()
    }
  }

  val widthPx: Int = (widthDp.value * service.resources.displayMetrics.density).roundToInt()
  val heightPx: Int = (heightDp.value * service.resources.displayMetrics.density).roundToInt()

  override val arcWidth = BubbleProperties.calcArcWidth(bubbleSizeScaleFactor)
  override val fontSize = BubbleProperties.calcFontSize(bubbleSizeScaleFactor)
  val viewHolder = TimerViewHolder(service, widthPx, heightPx)

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