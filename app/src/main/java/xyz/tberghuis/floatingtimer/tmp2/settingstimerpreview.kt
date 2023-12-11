package xyz.tberghuis.floatingtimer.tmp2

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.setValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import xyz.tberghuis.floatingtimer.composables.LocalHaloColour

// Vmc = View Model Component
// todo initialHaloColour
class SettingsTimerPreviewVmc(initialScale: Float = 0f) : BubbleProperties {
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

@Composable
fun SettingsTimerPreview(vmc: SettingsTimerPreviewVmc) {
  Text("Preview")
  Text("bubbleSizeScaleFactor ${vmc.bubbleSizeScaleFactor}")
  Text("bubbleSizeDp ${vmc.bubbleSizeDp}")
  Text("arcWidth ${vmc.arcWidth}")
  Text("fontSize ${vmc.fontSize}")

  CompositionLocalProvider(LocalHaloColour provides MaterialTheme.colorScheme.primary) {
    CompositionLocalProvider(
      LocalDensity provides Density(
        LocalDensity.current.density,
        1f
      )
    ) {
      TmpCountdownBubbleDisplay(vmc, 0.6f, 10)
    }
  }
}
