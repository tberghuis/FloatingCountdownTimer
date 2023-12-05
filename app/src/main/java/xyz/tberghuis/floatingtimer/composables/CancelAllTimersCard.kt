package xyz.tberghuis.floatingtimer.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import xyz.tberghuis.floatingtimer.R
import xyz.tberghuis.floatingtimer.viewmodels.HomeViewModel

@Composable
fun CancelAllTimersCard() {
  val vm: HomeViewModel = viewModel()
  ElevatedCard(
    modifier = Modifier
      .fillMaxWidth()
      .padding(15.dp),
  ) {
    Row(
      modifier = Modifier
        .padding(10.dp)
        .fillMaxWidth(),
      horizontalArrangement = Arrangement.Center
    ) {
      Button(onClick = {
        vm.cancelAllTimers()
      }) {
        Text(stringResource(R.string.cancel_all_timers))
      }
    }
  }
}