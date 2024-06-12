package xyz.tberghuis.floatingtimer.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import xyz.tberghuis.floatingtimer.R
import xyz.tberghuis.floatingtimer.data.RingtoneData
import xyz.tberghuis.floatingtimer.tmp5.TmpRingtoneTopBar
import xyz.tberghuis.floatingtimer.viewmodels.RingtoneScreenVm

@Composable
fun RingtoneScreen() {
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
  vm: RingtoneScreenVm = viewModel(),
) {
  val context = LocalContext.current
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
    item { Spacer(Modifier.height(5.dp)) }
    systemDefault(widthConstraint, vm)
    item { Spacer(Modifier.height(5.dp)) }
    ringtoneList(widthConstraint, context.getString(R.string.alarms), vm.alarmList.ringtoneList, vm)
    item { Spacer(Modifier.height(5.dp)) }
    ringtoneList(
      widthConstraint,
      context.getString(R.string.ringtones), vm.ringtoneList.ringtoneList, vm
    )
    item { Spacer(Modifier.height(5.dp)) }
    ringtoneList(
      widthConstraint,
      context.getString(R.string.notifications), vm.notificationList.ringtoneList, vm
    )
    item { Spacer(Modifier.height(5.dp)) }
  }
}

fun LazyListScope.currentRingtone(
  widthConstraint: Modifier,
  vm: RingtoneScreenVm,
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
        Text(stringResource(R.string.current))
      },
      colors = OutlinedTextFieldDefaults.colors(
        disabledTextColor = MaterialTheme.colorScheme.onSurface,
        disabledContainerColor = Color.Transparent,
        disabledBorderColor = MaterialTheme.colorScheme.outline,
        disabledLabelColor = MaterialTheme.colorScheme.primary,
      )
    )
  }

}


fun LazyListScope.systemDefault(
  widthConstraint: Modifier,
  vm: RingtoneScreenVm,
) {
  if (vm.systemDefaultAlarmVmc.systemDefaultRingtoneUri == null) {
    return
  }
  item {
    val context = LocalContext.current
    Row(
      widthConstraint,
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically,
    ) {
      Text(vm.systemDefaultAlarmVmc.systemDefaultRingtoneTitle,
        modifier = Modifier
          .clickable {
            vm.systemDefaultAlarmVmc.systemDefaultRingtoneUri?.let { uri ->
              vm.ringtonePreviewVmc.ringtoneClick(uri)
            }
          }
      )
      Button(
        onClick = {
          vm.systemDefaultAlarmVmc.systemDefaultRingtoneUri?.let { uri ->
            vm.setRingtone(uri)
          }
        },
        contentPadding = PaddingValues(
          start = 20.dp,
          top = 4.dp,
          end = 20.dp,
          bottom = 4.dp
        ),
      ) {
        Text(context.getString(R.string.apply))
      }
    }
  }
}

// doitwrong future.txt enum class for type
fun LazyListScope.ringtoneList(
  widthConstraint: Modifier,
  type: String,
  list: List<RingtoneData>,
  vm: RingtoneScreenVm,
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
    val context = LocalContext.current
    Row(
      widthConstraint,
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically,
    ) {
      Text(ringtoneData.title,
        modifier = Modifier.clickable {
          vm.ringtonePreviewVmc.ringtoneClick(ringtoneData.uri)
        }
      )
      Button(
        onClick = {
          vm.setRingtone(ringtoneData.uri)
        },
        contentPadding = PaddingValues(
          start = 20.dp,
          top = 4.dp,
          end = 20.dp,
          bottom = 4.dp
        ),
      ) {
        Text(context.getString(R.string.apply))
      }
    }
  }
}