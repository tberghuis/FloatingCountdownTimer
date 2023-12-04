package xyz.tberghuis.floatingtimer.tmp2

import androidx.compose.runtime.compositionLocalOf

val LocalFloatingService = compositionLocalOf<FloatingService> {
  error("CompositionLocal LocalFloatingService not present")
}