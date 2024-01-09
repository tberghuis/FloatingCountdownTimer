package xyz.tberghuis.floatingtimer.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import xyz.tberghuis.floatingtimer.LocalNavController
import xyz.tberghuis.floatingtimer.R
import xyz.tberghuis.floatingtimer.composables.PremiumDialog
import xyz.tberghuis.floatingtimer.composables.SettingsTimerPreviewCard
import xyz.tberghuis.floatingtimer.viewmodels.SettingsTimerPreviewVmc
import xyz.tberghuis.floatingtimer.viewmodels.SizeSettingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SizeSettingScreen(
  vm: SizeSettingViewModel = viewModel()
) {
  val navController = LocalNavController.current

  Scaffold(
    modifier = Modifier,
    topBar = {
      TopAppBar(
        title = { Text(stringResource(R.string.change_size)) },
        navigationIcon = {
          IconButton(onClick = {
            navController.navigateUp()
          }) {
            Icon(Icons.Filled.ArrowBack, stringResource(R.string.back))
          }
        },
        modifier = Modifier,
      )
    },
    snackbarHost = {},
  ) { padding ->
    SizeSettingScreenContent(padding)
  }

  PremiumDialog(vm.premiumVmc, stringResource(R.string.premium_reason_bubble_scale)) {
    vm.saveDefaultSize()
  }
}

@Composable
fun SizeSettingScreenContent(
  padding: PaddingValues = PaddingValues(),
  vm: SizeSettingViewModel = viewModel()
) {
  if (!vm.initialised) {
    return
  }

  Column(
    modifier = Modifier
      .padding(padding)
      .fillMaxSize()
      .verticalScroll(rememberScrollState()),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    Column(
      modifier = Modifier
        .widthIn(0.dp, 350.dp)
        .padding(15.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      SettingsTimerPreviewCard(vm.settingsTimerPreviewVmc)
      Spacer(Modifier.height(30.dp))
      SliderScale(vm.settingsTimerPreviewVmc)
      Spacer(Modifier.height(30.dp))
      SizeSettingScreenActions()

    }
  }
}

@Composable
fun SliderScale(
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

@Composable
fun SizeSettingScreenActions(
  vm: SizeSettingViewModel = viewModel()
) {
  val nav = LocalNavController.current
  Row(
    horizontalArrangement = Arrangement.spacedBy(10.dp),
  ) {
    Button(onClick = {
      nav.popBackStack()
    }) {
      Text(stringResource(R.string.cancel).uppercase())
    }
    Button(onClick = {
      vm.okButtonClick {
        vm.saveDefaultSize()
        nav.popBackStack()
      }
    }) {
      Text(stringResource(R.string.save).uppercase())
    }
  }
}