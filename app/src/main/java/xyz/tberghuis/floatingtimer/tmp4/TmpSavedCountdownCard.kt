package xyz.tberghuis.floatingtimer.tmp4

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import xyz.tberghuis.floatingtimer.logd


@OptIn(ExperimentalLayoutApi::class, ExperimentalFoundationApi::class)
@Composable
fun ColumnScope.TmpSavedCountdownCard(
  vm: TmpCountdownScreenVm = viewModel()
) {

  val savedTimers by vm.savedTimerFlow().collectAsState(
    initial = listOf()
  )

  Text("Saved")

  FlowRow(
    modifier = Modifier,
  ) {
    savedTimers.forEach { savedTimer ->
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
        Text("id: ${savedTimer.id}")
      }
    }
  }
}
