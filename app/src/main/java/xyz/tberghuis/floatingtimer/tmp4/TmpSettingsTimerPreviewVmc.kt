package xyz.tberghuis.floatingtimer.tmp4

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


// todo take argument of timer shape
class TmpSettingsTimerPreviewVmc(initialScale: Float, initialHaloColor: Color) :
  TmpBubbleProperties {
  var bubbleSizeScaleFactor by mutableFloatStateOf(initialScale) // 0<=x<=1
  override var haloColor by mutableStateOf(initialHaloColor)

  override val widthDp by derivedStateOf {
    TmpBubbleProperties.calcWidthDp(bubbleSizeScaleFactor)
  }

  // todo y= mx + b
  // m = 22, b=50
  // todo when(timerShape)
  override val heightDp by derivedStateOf {
    TmpBubbleProperties.calcRectHeightDp(bubbleSizeScaleFactor)
  }

  override val arcWidth by derivedStateOf {
    TmpBubbleProperties.calcArcWidth(bubbleSizeScaleFactor)
  }
  override val fontSize by derivedStateOf {
    TmpBubbleProperties.calcFontSize(bubbleSizeScaleFactor)
  }
}