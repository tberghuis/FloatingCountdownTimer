package xyz.tberghuis.floatingtimer.tmp4

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
  }
}