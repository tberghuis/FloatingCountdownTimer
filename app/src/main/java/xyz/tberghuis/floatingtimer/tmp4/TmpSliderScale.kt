package xyz.tberghuis.floatingtimer.tmp4

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import xyz.tberghuis.floatingtimer.tmp5.SettingsTimerPreviewVmc

@Composable
fun TmpSliderScale(
  vmc: SettingsTimerPreviewVmc
) {
  Slider(
    value = vmc.bubbleSizeScaleFactor,
    onValueChange = {
      vmc.bubbleSizeScaleFactor = it
    },
    modifier = Modifier.fillMaxWidth(),
    valueRange = 0f..1f,
  )
}