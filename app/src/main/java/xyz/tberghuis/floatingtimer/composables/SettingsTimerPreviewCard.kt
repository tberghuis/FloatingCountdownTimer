package xyz.tberghuis.floatingtimer.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import xyz.tberghuis.floatingtimer.R
import xyz.tberghuis.floatingtimer.service.countdown.CountdownViewDisplay
import xyz.tberghuis.floatingtimer.viewmodels.SettingsTimerPreviewVmc

@Composable
fun SettingsTimerPreviewCard(vmc: SettingsTimerPreviewVmc) {
  ElevatedCard(
    modifier = Modifier
      .height(400.dp)
      .fillMaxWidth()
  ) {
    Column(
      modifier = Modifier
        .padding(10.dp)
        .fillMaxSize(),
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      Text(stringResource(id = R.string.preview), fontSize = 20.sp)
      Column(
        modifier = Modifier
          .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
      ) {
        Box(
          contentAlignment = Alignment.Center,
        ) {
          CountdownViewDisplay(vmc, 0.6f, 59)
        }
      }
    }
  }
}