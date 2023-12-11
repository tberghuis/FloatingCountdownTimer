package xyz.tberghuis.floatingtimer.tmp2

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.setValue
import xyz.tberghuis.floatingtimer.ARC_WIDTH_NO_SCALE
import xyz.tberghuis.floatingtimer.TIMER_FONT_SIZE_NO_SCALE
import xyz.tberghuis.floatingtimer.TIMER_SIZE_NO_SCALE

// Vmc = View Model Component
// todo initialHaloColour
class SettingsTimerPreviewVmc(initialScale: Float = 0f) {
  var bubbleSizeScaleFactor by mutableFloatStateOf(initialScale) // 0<=x<=1
  val bubbleSizeDp by derivedStateOf {
    TIMER_SIZE_NO_SCALE * (bubbleSizeScaleFactor + 1)
  }
  val arcWidth by derivedStateOf {
    ARC_WIDTH_NO_SCALE * (0.9f * bubbleSizeScaleFactor + 1)
  }
  val fontSize by derivedStateOf {
    TIMER_FONT_SIZE_NO_SCALE * (1.2 * bubbleSizeScaleFactor + 1)
  }
}


@Composable
fun SettingsTimerPreview(vmc: SettingsTimerPreviewVmc) {
  Text("Preview")
  Text("bubbleSizeScaleFactor ${vmc.bubbleSizeScaleFactor}")
  Text("bubbleSizeDp ${vmc.bubbleSizeDp}")
  Text("arcWidth ${vmc.arcWidth}")
  Text("fontSize ${vmc.fontSize}")
}
