package xyz.tberghuis.floatingtimer.tmp4

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import xyz.tberghuis.floatingtimer.logd

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
  ConfirmDeleteSavedTimerDialog()
}

//@Composable
//fun ColumnScope.TmpCreateCountdownCard(
//  vm: TmpCountdownScreenVm = viewModel()
//) {
//  Text("Countdown")
//  Button(onClick = {
//    vm.addToSaved()
//  }) {
//    Text("save")
//  }
//  Button(onClick = {}) {
//    Text("create")
//  }
//
//}

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

@Composable
fun ConfirmDeleteSavedTimerDialog(
  vm: TmpCountdownScreenVm = viewModel()
) {

  if (vm.showDeleteDialog == null) {
    return
  }

  AlertDialog(
    onDismissRequest = {
      vm.showDeleteDialog = null
    },
    confirmButton = {
      TextButton(
        onClick = {
          vm.showDeleteDialog?.let {
            vm.deleteSavedTimer(it)
          }
          vm.showDeleteDialog = null
        }
      ) {
        Text("OK")
      }
    },
    modifier = Modifier,
    dismissButton = {
      TextButton(
        onClick = {
          vm.showDeleteDialog = null
        }
      ) {
        Text("Cancel")
      }
    },
    title = { Text("Delete Saved Timer?") },
  )
}

