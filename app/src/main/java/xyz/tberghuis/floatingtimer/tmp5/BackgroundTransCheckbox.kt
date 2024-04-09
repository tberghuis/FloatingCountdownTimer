package xyz.tberghuis.floatingtimer.tmp5

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

interface BackgroundTransCheckboxVm {
  var isBackgroundTransparent: Boolean
}

@Composable
fun ColumnScope.BackgroundTransCheckbox(vm: BackgroundTransCheckboxVm) {
  Row {
    Text("checkbox")

  }
}

@Preview
@Composable
fun PreviewBackgroundTransCheckbox() {
  val vm = remember {
    object : BackgroundTransCheckboxVm {
      override var isBackgroundTransparent by mutableStateOf(false)
    }
  }
  Column(
    Modifier
      .fillMaxSize()
  ) {
    BackgroundTransCheckbox(vm)
  }
}