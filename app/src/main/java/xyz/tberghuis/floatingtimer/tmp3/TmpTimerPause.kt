package xyz.tberghuis.floatingtimer.tmp3

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import xyz.tberghuis.floatingtimer.viewmodels.SettingsTimerPreviewVmc

@Composable
fun TmpTimerPause() {
  val vmc = SettingsTimerPreviewVmc(0f, Color.Blue)
  TmpCountdownViewDisplay(vmc, 0.6f, 59, true)
}