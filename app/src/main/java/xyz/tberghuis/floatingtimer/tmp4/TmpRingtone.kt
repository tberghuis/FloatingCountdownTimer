package xyz.tberghuis.floatingtimer.tmp4

import android.content.Intent
import android.media.RingtoneManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import xyz.tberghuis.floatingtimer.logd

@Composable
fun TmpRingtone(
  vm: TmpRingtoneVm = viewModel(),
) {


  Column(
    modifier = Modifier.padding(horizontal = 30.dp),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    Text("hello ringtone")
    Button(onClick = {
      vm.getRingtoneList()

    }) {
      Text("button")
    }

//    RingtonePicker()
  }
}

@Composable
fun RingtonePicker() {
  val launcher =
    rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
      logd("rememberLauncherForActivityResult")
    }
  Button(onClick = {
    val ringtonePickerIntent = Intent(RingtoneManager.ACTION_RINGTONE_PICKER)
    launcher.launch(
      ringtonePickerIntent
    )
  }) {
    Text("ringtone picker")
  }
}