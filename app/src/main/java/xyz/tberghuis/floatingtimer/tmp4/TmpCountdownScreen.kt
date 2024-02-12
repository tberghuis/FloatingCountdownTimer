package xyz.tberghuis.floatingtimer.tmp4

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.lifecycle.viewmodel.compose.viewModel
import xyz.tberghuis.floatingtimer.R
import xyz.tberghuis.floatingtimer.REQUEST_CODE_ACTION_MANAGE_OVERLAY_PERMISSION
import xyz.tberghuis.floatingtimer.logd

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TmpCountdownScreen(
  vm: TmpCountdownScreenVm = viewModel()
) {
  val context = LocalContext.current

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

  if (vm.grantOverlayVmc.showGrantOverlayDialog) {
    AlertDialog(onDismissRequest = {
      vm.grantOverlayVmc.showGrantOverlayDialog = false
    }, confirmButton = {
      Button(onClick = {
        logd("go to settings")
        val intent = Intent(
          Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + context.packageName)
        )

        startActivityForResult(
          context as Activity, intent, REQUEST_CODE_ACTION_MANAGE_OVERLAY_PERMISSION, null
        )
        vm.grantOverlayVmc.showGrantOverlayDialog = false

      }) {
        Text(stringResource(R.string.go_to_settings))
      }
    }, title = {
      Text(stringResource(R.string.enable_overlay_permission))
    }, text = {
      Text(buildAnnotatedString {
        append(stringResource(R.string.dialog_enable_overlay_permission))
        append(" ")
        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
          append(stringResource(R.string.app_name))
        }
      })
    })
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

