package xyz.tberghuis.floatingtimer.tmp4

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import xyz.tberghuis.floatingtimer.LocalNavController
import xyz.tberghuis.floatingtimer.R
import xyz.tberghuis.floatingtimer.logd

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen() {
  val navController = LocalNavController.current
  Scaffold(
    modifier = Modifier,
    topBar = {
      TopAppBar(
        title = { Text(stringResource(R.string.settings)) },
        navigationIcon = {
          IconButton(onClick = {
            navController.navigateUp()
          }) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, stringResource(R.string.back))
          }
        },
        modifier = Modifier,
      )
    },
    snackbarHost = {},
  ) { padding ->
    SettingsScreenContent(padding)
  }
}

// todo stringResource hardcoded strings

@Composable
fun SettingsScreenContent(
  padding: PaddingValues,
  vm: SettingsScreenVm = viewModel()
) {

  val looping by vm.loopingFlow.collectAsState(true)

  Column(
    modifier = Modifier
      .padding(padding)
      .fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {


    ListItem(
      headlineContent = { Text("Repeating alarm sound/vibration") },
//      overlineContent = { Text("overline") },
      supportingContent = { Text("off = ringtone will only play once") },
//      leadingContent = { Text("leadingContent") },
      trailingContent = {
        Switch(
          checked = looping,
          onCheckedChange = {
            logd("onCheckedChange $it")
            vm.updateLooping(it)
          },
        )

      },
    )

  }
}