package xyz.tberghuis.floatingtimer.composables

import android.provider.Settings
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import xyz.tberghuis.floatingtimer.R
import xyz.tberghuis.floatingtimer.countdown.CountdownOptions
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.screens.onFocusSelectAll
import xyz.tberghuis.floatingtimer.viewmodels.HomeViewModel

@Preview
@Composable
fun CreateCountdownCard() {
  val vm: HomeViewModel = viewModel()

  // todo must be an idiomatic way to center without the need
  // to specify Modifier.fillMaxWidth() and Center everywhere
  ElevatedCard(
    modifier = Modifier
      .fillMaxWidth()
      .padding(15.dp),
  ) {
    Row(
      modifier = Modifier
        .padding(10.dp)
        .fillMaxWidth(), horizontalArrangement = Arrangement.Center
    ) {
      Text(
        stringResource(id = R.string.countdown),
        modifier = Modifier.fillMaxWidth(),
        fontSize = 20.sp,
        textAlign = TextAlign.Center
      )
    }
    Row(
      modifier = Modifier
        .padding(10.dp)
        .fillMaxWidth(), horizontalArrangement = Arrangement.Center
    ) {
      TextField(
        modifier = Modifier
          .width(100.dp)
          .onFocusSelectAll(vm.minutes),
        label = { Text(stringResource(R.string.minutes)) },
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
        label = { Text(stringResource(R.string.seconds)) },
        value = vm.seconds.value,
        onValueChange = { vm.seconds.value = it },
        keyboardOptions = KeyboardOptions(
          keyboardType = KeyboardType.Number
        ),
        singleLine = true
      )
    }
    Row(
      modifier = Modifier
        .padding(10.dp)
        .fillMaxWidth(),
      horizontalArrangement = Arrangement.Center,
      verticalAlignment = Alignment.CenterVertically
    ) {
      CountdownOptions()
    }
    Row(
      modifier = Modifier
        .padding(10.dp)
        .fillMaxWidth(),
      horizontalArrangement = Arrangement.Center
    ) {
      CreateCountdownButton()
    }
  }
}

@Composable
fun CreateCountdownButton() {
  val vm: HomeViewModel = viewModel()
  val focusManager = LocalFocusManager.current
  val context = LocalContext.current

  Button(onClick = {
    logd("create")
    focusManager.clearFocus()
    if (!Settings.canDrawOverlays(context)) {
      vm.showGrantOverlayDialog = true
      return@Button
    }
    vm.countdownButtonClick()
  }) {
    Text(stringResource(R.string.create))
  }
}
