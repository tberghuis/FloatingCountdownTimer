package xyz.tberghuis.floatingtimer.tmp5

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

// todo take argument of timer shape, make timerShape sealed class or enum with room typeconverter
class SettingsTimerPreviewVmc(initialScale: Float, initialHaloColor: Color, timerShape: String) :
  BubbleProperties {
  var bubbleSizeScaleFactor by mutableFloatStateOf(initialScale) // 0<=x<=1
  override var haloColor by mutableStateOf(initialHaloColor)

  override val widthDp by derivedStateOf {
    BubbleProperties.calcWidthDp(bubbleSizeScaleFactor)
  }

  // todo y= mx + b
  // m = 22, b=50
  // todo when(timerShape)
  override val heightDp by derivedStateOf {
    when (timerShape) {
      "circle" -> {
        BubbleProperties.calcWidthDp(bubbleSizeScaleFactor)
      }
      "rectangle" -> {
        BubbleProperties.calcRectHeightDp(bubbleSizeScaleFactor)
      }
      // todo this should throw
      else -> {
        BubbleProperties.calcWidthDp(bubbleSizeScaleFactor)
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