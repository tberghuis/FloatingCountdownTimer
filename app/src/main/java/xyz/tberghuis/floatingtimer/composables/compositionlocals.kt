package xyz.tberghuis.floatingtimer.composables

import androidx.compose.runtime.compositionLocalOf
import xyz.tberghuis.floatingtimer.service.FloatingService
import xyz.tberghuis.floatingtimer.service.TimerViewHolder

val LocalFloatingService = compositionLocalOf<FloatingService> {
  error("CompositionLocal LocalFloatingService not present")
}

val LocalTimerViewHolder = compositionLocalOf<TimerViewHolder?> {
  null
}