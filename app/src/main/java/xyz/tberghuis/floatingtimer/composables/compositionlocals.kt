package xyz.tberghuis.floatingtimer.composables

import androidx.compose.runtime.compositionLocalOf
import xyz.tberghuis.floatingtimer.service.FloatingService
import androidx.compose.ui.graphics.Color

val LocalFloatingService = compositionLocalOf<FloatingService> {
  error("CompositionLocal LocalFloatingService not present")
}

val LocalHaloColour = compositionLocalOf<Color> {
  error("CompositionLocal LocalHaloColour not present")
}