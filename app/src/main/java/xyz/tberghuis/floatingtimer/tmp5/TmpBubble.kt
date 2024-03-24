package xyz.tberghuis.floatingtimer.tmp5

import android.util.Log
import android.view.WindowManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import kotlin.math.roundToInt
import xyz.tberghuis.floatingtimer.service.BubbleProperties
import xyz.tberghuis.floatingtimer.service.FloatingService

abstract class TmpBubble(
  private val service: FloatingService,
  bubbleSizeScaleFactor: Float,
  override val haloColor: Color,
  final override val timerShape: String
) : BubbleProperties {
  final override val widthDp = when (timerShape) {
    "label" -> {
      Dp.Unspecified
    }

    else -> {
      BubbleProperties.calcWidthDp(bubbleSizeScaleFactor)
    }
  }

  final override val heightDp = when (timerShape) {
    "circle" -> {
      BubbleProperties.calcWidthDp(bubbleSizeScaleFactor)
    }

    "rectangle" -> {
      BubbleProperties.calcRectHeightDp(bubbleSizeScaleFactor)
    }

    "label" -> {
      Dp.Unspecified
    }

    else -> {
      throw RuntimeException("invalid timer shape")
    }
  }

  val widthPx: Int = dimensionDpToPx(widthDp, service.resources.displayMetrics.density)
  val heightPx: Int = dimensionDpToPx(heightDp, service.resources.displayMetrics.density)

  override val arcWidth = BubbleProperties.calcArcWidth(bubbleSizeScaleFactor)
  override val fontSize = BubbleProperties.calcFontSize(bubbleSizeScaleFactor)
  val viewHolder = TmpTimerViewHolder(service, widthPx, heightPx)

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

fun dimensionDpToPx(dp: Dp, density: Float): Int {
  if (dp == Dp.Unspecified) {
    return WindowManager.LayoutParams.MATCH_PARENT
  }
  return (dp.value * density).roundToInt()
}
