package xyz.tberghuis.floatingtimer.tmp5

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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
  LazyColumn(
    modifier = Modifier
      .padding(padding)
      .fillMaxWidth()
  ) {
    item {
      OutlinedTextField(
        value = vm.currentRingtoneVmc.currentRingtoneTitle,
        onValueChange = {},
        modifier = Modifier.clickable {
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

    systemDefault(vm)

    ringtoneList("Alarms", vm.alarmList.ringtoneList, vm)
    ringtoneList("Ringtones", vm.ringtoneList.ringtoneList, vm)
    ringtoneList("Notifications", vm.notificationList.ringtoneList, vm)
  }
}

fun LazyListScope.systemDefault(
  vm: Tmp5RingtoneVm,
) {
  item {
    Text("todo system default")
  }
}

// doitwrong future.txt enum class for type
fun LazyListScope.ringtoneList(
  type: String,
  list: List<TmpRingtoneData>,
  vm: Tmp5RingtoneVm,
) {
  item {
    Column {
      // todo strings.xml
      Text(
        type,
        fontWeight = FontWeight.Bold
      )
      HorizontalDivider()
    }
  }
  items(list) { ringtoneData ->
    Row {
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