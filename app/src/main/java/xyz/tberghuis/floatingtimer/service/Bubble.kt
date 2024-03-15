package xyz.tberghuis.floatingtimer.service

import android.util.Log
import androidx.compose.ui.graphics.Color
import kotlin.math.roundToInt
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import xyz.tberghuis.floatingtimer.ARC_WIDTH_NO_SCALE
import xyz.tberghuis.floatingtimer.RECT_TIMER_HEIGHT_MAX_SCALE
import xyz.tberghuis.floatingtimer.RECT_TIMER_HEIGHT_NO_SCALE
import xyz.tberghuis.floatingtimer.TIMER_FONT_SIZE_NO_SCALE
import xyz.tberghuis.floatingtimer.TIMER_WIDTH_NO_SCALE
import xyz.tberghuis.floatingtimer.tmp5.TimerViewHolder

// future data class implements this, use composition over inheritance
// make update method a single method interface, does calcs and returns copy instance

// state that is static for lifetime of bubble goes here
interface BubbleProperties {
  val widthDp: Dp
  val heightDp: Dp

  val arcWidth: Dp
  val fontSize: TextUnit
  val haloColor: Color

  val timerShape: String

  companion object {
    fun calcWidthDp(scaleFactor: Float) = TIMER_WIDTH_NO_SCALE * (scaleFactor + 1)
    fun calcRectHeightDp(scaleFactor: Float): Dp {
      // y= mx + b
      // m = 22, b=50
      return (RECT_TIMER_HEIGHT_MAX_SCALE - RECT_TIMER_HEIGHT_NO_SCALE) * scaleFactor + RECT_TIMER_HEIGHT_NO_SCALE
    }

    fun calcArcWidth(scaleFactor: Float) = ARC_WIDTH_NO_SCALE * (0.9f * scaleFactor + 1)
    fun calcFontSize(scaleFactor: Float) = TIMER_FONT_SIZE_NO_SCALE * (1.2 * scaleFactor + 1)
  }
}

abstract class Bubble(
  private val service: FloatingService,
  bubbleSizeScaleFactor: Float,
  override val haloColor: Color,
  final override val timerShape: String
) : BubbleProperties {
  final override val widthDp = BubbleProperties.calcWidthDp(bubbleSizeScaleFactor)

  final override val heightDp = when (timerShape) {
    "circle" -> {
      BubbleProperties.calcWidthDp(bubbleSizeScaleFactor)
    }

    "rectangle" -> {
      BubbleProperties.calcRectHeightDp(bubbleSizeScaleFactor)
    }

    else -> {
      throw RuntimeException("invalid timer shape")
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