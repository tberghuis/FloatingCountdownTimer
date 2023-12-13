package xyz.tberghuis.floatingtimer.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.godaddy.android.colorpicker.ClassicColorPicker
import xyz.tberghuis.floatingtimer.LocalNavController
import xyz.tberghuis.floatingtimer.R
import xyz.tberghuis.floatingtimer.composables.LocalHaloColour
import xyz.tberghuis.floatingtimer.composables.PremiumDialog
import xyz.tberghuis.floatingtimer.tmp2.SettingsTimerPreviewCard
import xyz.tberghuis.floatingtimer.viewmodels.ColorSettingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorSettingScreen(
  vm: ColorSettingViewModel = viewModel()
) {
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
            Icon(Icons.Filled.ArrowBack, stringResource(R.string.back))
          }
        },
        modifier = Modifier,
      )
    },
    snackbarHost = {},
  ) { padding ->
    if (!vm.initialised) {
      return@Scaffold
    }

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val colorPickerWidth =
      if (screenWidth < 350.dp) Modifier.fillMaxWidth() else Modifier.widthIn(0.dp, 300.dp)

    Column(
      modifier = Modifier
        .padding(padding)
        .fillMaxSize()
        .verticalScroll(rememberScrollState()),
      verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically),
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      Text(stringResource(R.string.change_timer_color), fontSize = 20.sp)

      val previewHaloColor = vm.colorPickerColorState.value.toColor()
      CompositionLocalProvider(LocalHaloColour provides previewHaloColor) {
        SettingsTimerPreviewCard(vm.settingsTimerPreviewVmc)
      }

      ClassicColorPicker(
        modifier = Modifier
          .height(300.dp)
          .then(colorPickerWidth),
        colorState = vm.colorPickerColorState
      )

      Button(onClick = {
        vm.saveHaloColorClick()
      }) {
        // todo make this an icon
        Text(stringResource(R.string.save).uppercase())
      }
    }
  }

  PremiumDialog(vm.premiumVmc, stringResource(R.string.premium_reason_halo_colour)) {
    vm.saveHaloColor()
  }
}