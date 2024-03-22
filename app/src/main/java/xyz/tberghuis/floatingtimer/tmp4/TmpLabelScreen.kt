package xyz.tberghuis.floatingtimer.tmp4

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TmpLabelScreen(
//  vm: TmpVm = viewModel()
) {
  Column(
    modifier = Modifier.padding(horizontal = 30.dp),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    Text("hello label screen")
  }
}