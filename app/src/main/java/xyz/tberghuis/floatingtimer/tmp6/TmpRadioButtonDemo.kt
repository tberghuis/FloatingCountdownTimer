package xyz.tberghuis.floatingtimer.tmp6

import android.app.Application
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

class TmpRadioButtonVm(private val application: Application) : AndroidViewModel(application),
  BubbleShapeChoiceVm {
  override var bubbleShapeChoice by mutableStateOf("circle")
}

@Composable
fun TmpRadioButtonDemo(
  vm: TmpRadioButtonVm = viewModel()
) {
  Text("hello radio button")
  BubbleShapeChoice(vm)
}
