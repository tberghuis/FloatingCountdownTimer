package xyz.tberghuis.floatingtimer.screens

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.godaddy.android.colorpicker.ClassicColorPicker
import xyz.tberghuis.floatingtimer.LocalNavController
import xyz.tberghuis.floatingtimer.PROGRESS_ARC_WIDTH
import xyz.tberghuis.floatingtimer.TIMER_SIZE_DP
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.service.countdown.ProgressArc
import xyz.tberghuis.floatingtimer.tmp.LocalHaloColour
import xyz.tberghuis.floatingtimer.viewmodels.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
  vm: SettingsViewModel = viewModel()
) {
  val navController = LocalNavController.current
  val context = LocalContext.current

  Scaffold(
    modifier = Modifier,
    topBar = {
      TopAppBar(
        title = { Text("Settings") },
        navigationIcon = {
          IconButton(onClick = {
            navController.navigateUp()
          }) {
            Icon(Icons.Filled.ArrowBack, "back")
          }
        },
        modifier = Modifier,
      )
    },
    snackbarHost = {},
  ) { padding ->

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
      Text("Change Timer color", fontSize = 20.sp)

      // derived state of???
      val previewHaloColor = vm.colorPickerColorState.value.toColor()
      CompositionLocalProvider(LocalHaloColour provides previewHaloColor) {
        TimerPreview()
      }

      ClassicColorPicker(
        modifier = Modifier
          .height(300.dp)
          .then(colorPickerWidth),
        colorState = vm.colorPickerColorState
      )

      Button(onClick = {
        logd("change timer color")
        if (vm.haloColourPurchased) {
          vm.saveHaloColor()
        } else {
          vm.showPurchaseDialog = true
        }
      }) {
        Text("SAVE")
      }
    }
  }

  if (vm.showPurchaseDialog) {
    AlertDialog(
      onDismissRequest = {
        vm.showPurchaseDialog = false
      },
      confirmButton = {
        TextButton(onClick = {
          vm.buy(context as Activity)
        }) {
          Text("Buy".uppercase())
        }
      },
      modifier = Modifier,
      dismissButton = {
        TextButton(onClick = { vm.showPurchaseDialog = false }) {
          Text("Cancel".uppercase())
        }
      },
      title = { Text("Premium Feature") },
      text = {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
        ) {
          Text("Change Timer halo color")
          Text(vm.haloColorChangePriceText)
        }
      },
    )
  }
}


@Composable
fun TimerPreview() {
  Box(
    modifier = Modifier
      .size(TIMER_SIZE_DP.dp)
      .padding(PROGRESS_ARC_WIDTH / 2),
    contentAlignment = Alignment.Center
  ) {
    ProgressArc(59 / 90.toFloat())
    // future fix font to match overlay (without theme)
    Text("00:59", color = Color.Black)
  }
}