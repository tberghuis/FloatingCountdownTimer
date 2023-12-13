package xyz.tberghuis.floatingtimer.viewmodels

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.setValue
import xyz.tberghuis.floatingtimer.service.BubbleProperties

// Vmc = View Model Component
class SettingsTimerPreviewVmc(initialScale: Float) : BubbleProperties {
  var bubbleSizeScaleFactor by mutableFloatStateOf(initialScale) // 0<=x<=1
  override val bubbleSizeDp by derivedStateOf {
    BubbleProperties.calcBubbleSizeDp(bubbleSizeScaleFactor)
  }
  override val arcWidth by derivedStateOf {
    BubbleProperties.calcArcWidth(bubbleSizeScaleFactor)
  }
  override val fontSize by derivedStateOf {
    BubbleProperties.calcFontSize(bubbleSizeScaleFactor)
  }
}