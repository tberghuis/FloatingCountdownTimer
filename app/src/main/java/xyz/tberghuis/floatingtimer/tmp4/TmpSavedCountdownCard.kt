package xyz.tberghuis.floatingtimer.tmp4

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.service.countdown.CountdownViewDisplay
import xyz.tberghuis.floatingtimer.viewmodels.SettingsTimerPreviewVmc


@OptIn(ExperimentalLayoutApi::class, ExperimentalFoundationApi::class)
@Composable
fun ColumnScope.TmpSavedCountdownCard(
  vm: TmpCountdownScreenVm = viewModel()
) {

  val savedTimers by vm.savedCountdownFlow().collectAsState(
    initial = listOf()
  )

// tmp


  ElevatedCard(
    modifier = Modifier
      .fillMaxWidth()
      .padding(15.dp),
  ) {

    Text(
      "Saved",
      modifier = Modifier
        .padding(10.dp)
        .align(Alignment.CenterHorizontally),
      fontSize = 20.sp,
      textAlign = TextAlign.Center
    )

    FlowRow(
      modifier = Modifier,
    ) {
      savedTimers.forEach { savedTimer ->
        val c = Color(savedTimer.timerColor)
        val settingsTimerPreviewVmc = SettingsTimerPreviewVmc(0f, c)
        Box(
          modifier = Modifier.combinedClickable(
            onClick = {
              logd("onClick")
            },
            onLongClick = {
              logd("onLongClick")
              vm.showDeleteDialog = savedTimer
            },
          ),
        ) {
          CountdownViewDisplay(settingsTimerPreviewVmc, 1f, savedTimer.durationSeconds, false)
        }
      }
    }
  }
}
