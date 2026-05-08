package xyz.tberghuis.floatingtimer.composables

import androidx.compose.runtime.compositionLocalOf
import xyz.tberghuis.floatingtimer.tmp.tmp03.FloatingService
import xyz.tberghuis.floatingtimer.tmp.tmp03.TimerViewHolder

val LocalFloatingService = compositionLocalOf<FloatingService> {
  error("CompositionLocal LocalFloatingService not present")
}

val LocalTimerViewHolder = compositionLocalOf<TimerViewHolder?> {
  null
}