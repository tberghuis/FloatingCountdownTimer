package xyz.tberghuis.floatingtimer.tmp2

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import xyz.tberghuis.floatingtimer.LocalNavController
import xyz.tberghuis.floatingtimer.R
import xyz.tberghuis.floatingtimer.ui.theme.FloatingTimerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangeSizeScreen(
  vm: ChangeSizeViewModel = viewModel()
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
    ChangeSizeScreenContent(padding)
  }
}

@Composable
fun ChangeSizeScreenContent(
  padding: PaddingValues = PaddingValues(),
  vm: ChangeSizeViewModel = viewModel()
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
      TmpSliderScale(vm.settingsTimerPreviewVmc)
      Spacer(Modifier.height(30.dp))
      Button(onClick = {
        vm.saveChangeSize()
      }) {
        Text(stringResource(R.string.save).uppercase())
      }
    }
  }
}

@Composable
fun TmpSliderScale(
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

@Preview()
@Composable
fun PreviewChangeSizeScreenContent() {
  FloatingTimerTheme {
    Surface(
      modifier = Modifier
        .fillMaxSize()
        .background(Color.White),
    ) {
      ChangeSizeScreenContent(vm = ChangeSizeViewModel(Application()))
    }
  }
}