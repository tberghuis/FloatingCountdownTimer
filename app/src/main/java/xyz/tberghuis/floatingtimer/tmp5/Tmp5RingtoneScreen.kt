package xyz.tberghuis.floatingtimer.tmp5

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import xyz.tberghuis.floatingtimer.LocalNavController

@Composable
fun TmpNavHost() {
  val navController = rememberNavController()
  CompositionLocalProvider(LocalNavController provides navController) {
    Tmp5RingtoneScreen()
  }
}

@Composable
fun Tmp5RingtoneScreen() {
  Scaffold(
    modifier = Modifier,
    topBar = { TmpRingtoneTopBar() },
    snackbarHost = {},
  ) { padding ->
    Tmp5RingtoneScreenContent(padding)
  }
}

@Composable
fun Tmp5RingtoneScreenContent(
  padding: PaddingValues,
  vm: Tmp5RingtoneVm = viewModel(),
) {

  val widthConstraint = Modifier
    .widthIn(max = 350.dp)
    .fillMaxWidth()

  LazyColumn(
    modifier = Modifier
      .padding(padding)
      .fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    currentRingtone(widthConstraint, vm)
    systemDefault(widthConstraint, vm)
    ringtoneList(widthConstraint, "Alarms", vm.alarmList.ringtoneList, vm)
    ringtoneList(widthConstraint, "Ringtones", vm.ringtoneList.ringtoneList, vm)
    ringtoneList(widthConstraint, "Notifications", vm.notificationList.ringtoneList, vm)
  }
}

fun LazyListScope.currentRingtone(
  widthConstraint: Modifier,
  vm: Tmp5RingtoneVm,
) {
  item {
    OutlinedTextField(
      value = vm.currentRingtoneVmc.currentRingtoneTitle,
      onValueChange = {},
      modifier = Modifier
        .then(widthConstraint)
        .clickable {
          vm.currentRingtoneVmc.currentRingtoneUri?.let {
            vm.ringtonePreviewVmc.ringtoneClick(it)
          }
        },
      enabled = false,
      readOnly = true,
      label = {
        Text("current")
      },
      colors = OutlinedTextFieldDefaults.colors(
        disabledTextColor = MaterialTheme.colorScheme.onSurface,
        disabledContainerColor = Color.Transparent,
        disabledBorderColor = MaterialTheme.colorScheme.outline,
        disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
      )
    )
  }

}


fun LazyListScope.systemDefault(
  widthConstraint: Modifier,
  vm: Tmp5RingtoneVm,
) {
  if (vm.systemDefaultAlarmVmc.systemDefaultRingtoneUri == null) {
    return
  }
  item {
    Row(widthConstraint) {
      Text(vm.systemDefaultAlarmVmc.systemDefaultRingtoneTitle,
        modifier = Modifier
          .clickable {
            vm.systemDefaultAlarmVmc.systemDefaultRingtoneUri?.let { uri ->
              vm.ringtonePreviewVmc.ringtoneClick(uri)
            }
          }
      )
      Button(onClick = {
        vm.systemDefaultAlarmVmc.systemDefaultRingtoneUri?.let { uri ->
          vm.setRingtone(uri)
        }
      }) {
        Text("Apply")
      }
    }
  }
}

// doitwrong future.txt enum class for type
fun LazyListScope.ringtoneList(
  widthConstraint: Modifier,
  type: String,
  list: List<TmpRingtoneData>,
  vm: Tmp5RingtoneVm,
) {
  item {
    Column(widthConstraint) {
      // todo strings.xml
      Text(
        type,
        fontWeight = FontWeight.Bold
      )
      HorizontalDivider()
    }
  }
  items(list) { ringtoneData ->
    Row(widthConstraint) {
      Text(ringtoneData.title,
        modifier = Modifier.clickable {
          vm.ringtonePreviewVmc.ringtoneClick(ringtoneData.uri)
        }
      )
      Button(onClick = {
        vm.setRingtone(ringtoneData.uri)
      }) {
        Text("Apply")
      }
    }
  }
}