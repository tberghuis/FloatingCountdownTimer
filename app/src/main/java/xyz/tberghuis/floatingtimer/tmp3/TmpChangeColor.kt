package xyz.tberghuis.floatingtimer.tmp3

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun TmpChangeColor(
  vm: TmpChangeColorVm = viewModel()
) {
  Column {
    Text("tmp change color")
  }
}

class TmpChangeColorVm : ViewModel() {
  val fdfsd = "fdsfs"
}