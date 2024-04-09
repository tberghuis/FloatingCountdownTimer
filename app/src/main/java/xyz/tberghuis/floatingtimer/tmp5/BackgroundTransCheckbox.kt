package xyz.tberghuis.floatingtimer.tmp5

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import xyz.tberghuis.floatingtimer.R

interface BackgroundTransCheckboxVm {
  var isBackgroundTransparent: Boolean
}

@Composable
fun ColumnScope.BackgroundTransCheckbox(vm: BackgroundTransCheckboxVm) {
  Row(
    modifier = Modifier
      .align(Alignment.CenterHorizontally),
//      .padding(10.dp),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Checkbox(
      checked = vm.isBackgroundTransparent,
      onCheckedChange = {
        vm.isBackgroundTransparent = it
      },
    )
    Text(stringResource(R.string.transparent_background))
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
      .border(3.dp, Color.Red),
    verticalArrangement = Arrangement.Center,
  ) {
    BackgroundTransCheckbox(vm)
  }
}