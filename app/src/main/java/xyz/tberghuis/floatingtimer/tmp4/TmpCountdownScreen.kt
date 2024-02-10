package xyz.tberghuis.floatingtimer.tmp4

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TmpCountdownScreen() {
  Scaffold(
    modifier = Modifier,
    topBar = {
      TopAppBar(
        title = { Text("Floating Timer") },
        modifier = Modifier,
      )
    },
    bottomBar = {
      TmpBottomBar(TmpScreenTypeCountdown)
    },
  ) { padding ->
    TmpCountdownScreenContent(padding)
  }
}

@Composable
fun TmpCountdownScreenContent(padding: PaddingValues) {
  Column(modifier = Modifier.padding(padding)) {
    TmpCreateCountdownCard()
    TmpSavedCountdownCard()
  }
}

@Composable
fun ColumnScope.TmpCreateCountdownCard(
  vm: TmpCountdownScreenVm = viewModel()
) {
  Text("Countdown")
  Button(onClick = {
    vm.addToSaved()
  }) {
    Text("save")
  }
  Button(onClick = {}) {
    Text("create")
  }

}

@OptIn(ExperimentalLayoutApi::class)
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
      Text("id: ${savedTimer.id}")
    }
  }
}
