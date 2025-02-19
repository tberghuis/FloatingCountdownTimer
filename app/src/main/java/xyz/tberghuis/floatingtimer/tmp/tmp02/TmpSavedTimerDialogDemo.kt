package xyz.tberghuis.floatingtimer.tmp.tmp02

import android.app.Application
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

class TmpCountdownScreenVm(
  application: Application,
) :
  AndroidViewModel(application) {
  // this is fake CountdownScreenVm
  val willitblend = "will it blend"
}

@Preview
@Composable
fun TmpSavedTimerDialogDemo(
  vm: TmpCountdownScreenVm = viewModel()
) {
  // todo fake savedtimer on long press
  Column {
    Text("hello ${vm.willitblend}")
  }
}