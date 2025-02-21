package xyz.tberghuis.floatingtimer.screens

import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import xyz.tberghuis.floatingtimer.R
import xyz.tberghuis.floatingtimer.tmp.tmp01.DeepLinkScreenVm

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeepLinkScreen(
  vm: DeepLinkScreenVm = viewModel()
) {
  val activity = LocalActivity.current
  Scaffold(
    modifier = Modifier.fillMaxSize(),
    topBar = {
      TopAppBar(title = {
        Text(stringResource(R.string.app_name))
      })
    },
    bottomBar = {
      BottomAppBar(
        modifier = Modifier,
        contentPadding = PaddingValues(10.dp),
      ) {
        TextButton(onClick = {
          activity?.let {
            vm.openFloatingTimer(activity)
          }
        }) {
          Text(stringResource(R.string.open_app))
        }
        Spacer(Modifier.weight(1f))
        TextButton(onClick = {
          activity?.finish()
        }) {
          Text(stringResource(R.string.close))
        }
      }
    },
  ) { innerPadding ->
    Column(
      modifier = Modifier
        .padding(innerPadding)
        .padding(horizontal = 10.dp),
      verticalArrangement = Arrangement.spacedBy(15.dp),
      horizontalAlignment = Alignment.Start,
    ) {
      Spacer(Modifier.height(50.dp))
      Text("${stringResource(R.string.link)}: ${vm.uiLink}")
      Text("${stringResource(R.string.timer_type)}: ${vm.uiTimerType}")
      Text("${stringResource(R.string.auto_start)}: ${vm.uiStart}")
      Text("${stringResource(R.string.result)}: ${vm.uiResult}")
    }
  }
}