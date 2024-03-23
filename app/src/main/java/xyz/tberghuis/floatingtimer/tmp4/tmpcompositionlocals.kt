package xyz.tberghuis.floatingtimer.tmp4

import androidx.compose.runtime.compositionLocalOf
import xyz.tberghuis.floatingtimer.service.TimerViewHolder

val LocalTimerViewHolder = compositionLocalOf<TimerViewHolder> {
  error("CompositionLocal LocalTimerViewHolder not present")
}