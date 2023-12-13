package xyz.tberghuis.floatingtimer.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import xyz.tberghuis.floatingtimer.R
import xyz.tberghuis.floatingtimer.service.countdown.CountdownViewDisplay
import xyz.tberghuis.floatingtimer.viewmodels.SettingsTimerPreviewVmc

@Composable
fun SettingsTimerPreviewCard(vmc: SettingsTimerPreviewVmc) {
  ElevatedCard(
    modifier = Modifier
      .height(180.dp)
      .fillMaxWidth()
  ) {
    Row(
      modifier = Modifier
        .padding(10.dp)
        .fillMaxSize(),
      horizontalArrangement = Arrangement.Center,
      verticalAlignment = Alignment.CenterVertically,
    ) {
      Text(stringResource(id = R.string.preview), fontSize = 20.sp)
      Box(
        modifier = Modifier
          .width(140.dp),
        contentAlignment = Alignment.Center,
      ) {
        CountdownViewDisplay(vmc, 0.6f, 59)
      }
    }
  }
}