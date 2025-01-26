package xyz.tberghuis.floatingtimer.composables

import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import xyz.tberghuis.floatingtimer.LocalNavController
import xyz.tberghuis.floatingtimer.R
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.viewmodels.CountdownScreenVm
import xyz.tberghuis.floatingtimer.viewmodels.SharedVm

@Preview
@Composable
fun CreateCountdownCard() {
  val vm: CountdownScreenVm = viewModel()
  val focusManager = LocalFocusManager.current
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
        .widthIn(max = 350.dp),
      horizontalArrangement = Arrangement.spacedBy(10.dp, alignment = Alignment.CenterHorizontally)
    ) {
      CountdownTimeField(vm.hours, stringResource(R.string.hours), imeAction = ImeAction.Next)
      CountdownTimeField(
        vm.minutes,
        stringResource(R.string.minutes),
        "CountdownMinutes",
        imeAction = ImeAction.Next
      )
      CountdownTimeField(vm.seconds, stringResource(R.string.seconds))
    }

    TimerShapeChoice(vm)
    CountdownOptions()
    Row(
      modifier = Modifier
        .padding(10.dp)
        .fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(10.dp, alignment = Alignment.CenterHorizontally),
      verticalAlignment = Alignment.CenterVertically,
    ) {
      ChangeTimerColorButton("change_color/countdown", vm.haloColor)
      AddSavedButton {
        focusManager.clearFocus()
        vm.addToSaved()
      }
      CreateCountdownButton()
    }
  }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CreateCountdownButton() {
  val vm: CountdownScreenVm = viewModel()
  val focusManager = LocalFocusManager.current
  val context = LocalContext.current
  val sharedVm: SharedVm = viewModel(context as ComponentActivity)
  Button(
    onClick = {
      logd("create")
      focusManager.clearFocus()
      if (!Settings.canDrawOverlays(context)) {
        sharedVm.showGrantOverlayDialog = true
        return@Button
      }
      vm.countdownButtonClick()
    },
    modifier = Modifier
      .semantics { testTagsAsResourceId = true }
      .testTag("CreateCountdownButton"),

    ) {
    Text(stringResource(R.string.create))
  }
}

@Composable
fun ColumnScope.CountdownOptions() {
  val vm: CountdownScreenVm = viewModel()
  val nav = LocalNavController.current

  // doitwrong
  val vibration = vm.vibrationFlow.collectAsState(false)
  val sound = vm.soundFlow.collectAsState(false)

  Column(
    modifier = Modifier
      .width(IntrinsicSize.Max)
      .align(Alignment.CenterHorizontally)
  ) {
    BackgroundTransCheckbox(vm = vm)
    Row(verticalAlignment = Alignment.CenterVertically) {
      Checkbox(
        checked = vibration.value,
        onCheckedChange = {
          vm.updateVibration(it)
        }
      )
      Text(stringResource(R.string.vibration))
    }
    Row(verticalAlignment = Alignment.CenterVertically) {
      Checkbox(
        checked = sound.value,
        onCheckedChange = {
          vm.updateSound(it)
        }
      )
      Text("${stringResource(R.string.sound)} ")
      Text(
        text = vm.currentRingtoneVmc.currentRingtoneTitle,
        modifier = Modifier
          .clickable {
            nav.navigate("countdown_ringtone")
          },
        color = MaterialTheme.colorScheme.primary,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        style = LocalTextStyle.current.copy(textDecoration = TextDecoration.Underline)
      )
    }
  }
}