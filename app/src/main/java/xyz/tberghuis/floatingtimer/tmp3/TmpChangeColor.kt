package xyz.tberghuis.floatingtimer.tmp3

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import xyz.tberghuis.floatingtimer.logd

@Composable
fun TmpChangeColor(
  vm: TmpChangeColorVm = viewModel()
) {
  Column {
    Text("tmp change color")
    Button(onClick = {
      vm.setResultAndPopHome()
    }) {
      Text("button")
    }
  }
}

class TmpChangeColorVm : ViewModel() {
  val fdfsd = "fdsfs"


  fun setResultAndPopHome() {
    logd("setResultAndPopHome")
  }

}