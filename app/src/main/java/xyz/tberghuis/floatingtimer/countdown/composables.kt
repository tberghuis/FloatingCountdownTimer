package xyz.tberghuis.floatingtimer.countdown

import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import xyz.tberghuis.floatingtimer.EXTRA_TIMER_DURATION
import xyz.tberghuis.floatingtimer.ForegroundService
import xyz.tberghuis.floatingtimer.INTENT_COMMAND
import xyz.tberghuis.floatingtimer.INTENT_COMMAND_CREATE_TIMER
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.screens.onFocusSelectAll
import xyz.tberghuis.floatingtimer.viewmodels.HomeViewModel


@Composable
fun CreateCountdownCard() {
  val vm: HomeViewModel = hiltViewModel()
  val focusManager = LocalFocusManager.current
  val context = LocalContext.current

  Column {
    Row {
      TextField(
        modifier = Modifier
          .width(100.dp)
          .onFocusSelectAll(vm.minutes),
        label = { Text("minutes") },
        value = vm.minutes.value,
        onValueChange = { vm.minutes.value = it },
        keyboardOptions = KeyboardOptions(
          keyboardType = KeyboardType.Number
        ),
        singleLine = true
      )
      Spacer(Modifier.width(20.dp))
      TextField(
        modifier = Modifier
          .width(100.dp)
//          .padding(vertical = 20.dp)
          .onFocusSelectAll(vm.seconds),
        label = { Text("seconds") },
        value = vm.seconds.value,
        onValueChange = { vm.seconds.value = it },
        keyboardOptions = KeyboardOptions(
          keyboardType = KeyboardType.Number
        ),
        singleLine = true
      )
    }

    Button(onClick = {
      logd("create")
      focusManager.clearFocus()
      if (!Settings.canDrawOverlays(context)) {
        vm.showGrantOverlayDialog = true
        return@Button
      }
      logd("after canDrawOverlays")
      val min: Int
      val sec: Int
      try {
        min = vm.minutes.value.text.toInt()
        sec = vm.seconds.value.text.toInt()
      } catch (e: NumberFormatException) {
        // todo show dialog
        return@Button
      }
      val totalSecs = min * 60 + sec
      if (totalSecs == 0) {
        // todo show dialog
        return@Button
      }
      createTimer(context, totalSecs)
    }) {
      Text("create")
    }


  }
}


fun createTimer(context: Context, duration: Int) {
  val intent = Intent(context.applicationContext, ForegroundService::class.java)
  intent.putExtra(INTENT_COMMAND, INTENT_COMMAND_CREATE_TIMER)
  intent.putExtra(EXTRA_TIMER_DURATION, duration)
  context.startForegroundService(intent)
}