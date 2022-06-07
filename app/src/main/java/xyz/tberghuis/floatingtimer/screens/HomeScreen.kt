package xyz.tberghuis.floatingtimer.screens

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.hilt.navigation.compose.hiltViewModel
import xyz.tberghuis.floatingtimer.EXTRA_TIMER_DURATION
import xyz.tberghuis.floatingtimer.ForegroundService
import xyz.tberghuis.floatingtimer.INTENT_COMMAND
import xyz.tberghuis.floatingtimer.INTENT_COMMAND_CREATE_TIMER
import xyz.tberghuis.floatingtimer.REQUEST_CODE_ACTION_MANAGE_OVERLAY_PERMISSION
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.viewmodels.HomeViewModel
import java.lang.NumberFormatException

@Composable
fun HomeScreen() {
  val vm: HomeViewModel = hiltViewModel()
  val context = LocalContext.current

  Scaffold(
    topBar = {
      TopAppBar(title = {
        Text("Floating Countdown Timer")
      })
    },
    content = {
      HomeScreenContent()
    }
  )

  if (vm.showGrantOverlayDialog) {
    AlertDialog(onDismissRequest = {
      vm.showGrantOverlayDialog = false
    },
      confirmButton = {
        Button(onClick = {
          logd("go to settings")
          val intent = Intent(
            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
            Uri.parse("package:" + context.packageName)
          )

          startActivityForResult(
            context as Activity,
            intent,
            REQUEST_CODE_ACTION_MANAGE_OVERLAY_PERMISSION, null
          )
          vm.showGrantOverlayDialog = false

        }) {
          Text("Go To Settings")
        }
      },
      title = {
        Text("Enable Overlay Permission")
      },
      text = {
        Text(buildAnnotatedString {
          append("Please enable \"Allow display over other apps\" permission for application ")
          withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
            append("Floating Countdown Timer")
          }
        })
      }
    )
  }
}

@Composable
fun HomeScreenContent() {
  val vm: HomeViewModel = hiltViewModel()
  val context = LocalContext.current
  val focusManager = LocalFocusManager.current

  Column(
    modifier = Modifier
//      .background(Color.Yellow)
      .fillMaxSize()
      .pointerInput(Unit) {
        detectTapGestures(onTap = {
          focusManager.clearFocus()
          logd("on tap")
        })
      },
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
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
    }

    Row {
      TextField(
        modifier = Modifier
          .width(100.dp)
          .padding(vertical = 20.dp)
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

fun Modifier.onFocusSelectAll(textFieldValueState: MutableState<TextFieldValue>): Modifier =
  composed(
    inspectorInfo = debugInspectorInfo {
      name = "textFieldValueState"
      properties["textFieldValueState"] = textFieldValueState
    }
  ) {
    var triggerEffect by remember {
      mutableStateOf<Boolean?>(null)
    }
    if (triggerEffect != null) {
      LaunchedEffect(triggerEffect) {
        val tfv = textFieldValueState.value
        textFieldValueState.value = tfv.copy(selection = TextRange(0, tfv.text.length))
      }
    }
    Modifier.onFocusChanged { focusState ->
      if (focusState.isFocused) {
        triggerEffect = triggerEffect?.let { bool ->
          !bool
        } ?: true
      }
    }
  }
