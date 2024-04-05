package xyz.tberghuis.floatingtimer.viewmodels

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import xyz.tberghuis.floatingtimer.service.BubbleProperties

// todo make timerShape sealed class or enum with room typeconverter
class SettingsTimerPreviewVmc(
  initialScale: Float,
  initialHaloColor: Color,
  override val timerShape: String,
  override val label: String? = null
) : BubbleProperties {
  var bubbleSizeScaleFactor by mutableFloatStateOf(initialScale) // 0<=x<=1
  override var haloColor by mutableStateOf(initialHaloColor)
  override val widthDp by derivedStateOf {
    when (timerShape) {
      "circle" -> {
        BubbleProperties.calcCountdownTimerSizeDp(bubbleSizeScaleFactor)
      }

      else -> {
        Dp.Unspecified
      }
    }
  }
  override val heightDp by derivedStateOf {
    when (timerShape) {
      "circle" -> {
        BubbleProperties.calcCountdownTimerSizeDp(bubbleSizeScaleFactor)
      }

      else -> {
        Dp.Unspecified
      }
    }
  }
  override val arcWidth by derivedStateOf {
    BubbleProperties.calcArcWidth(bubbleSizeScaleFactor)
  }
  override val fontSize by derivedStateOf {
    BubbleProperties.calcFontSize(bubbleSizeScaleFactor)
  }
}