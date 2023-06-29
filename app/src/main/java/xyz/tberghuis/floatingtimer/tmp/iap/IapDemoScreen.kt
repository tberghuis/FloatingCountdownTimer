package xyz.tberghuis.floatingtimer.tmp.iap

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun IapDemoScreen(
  vm: IapDemoVm = viewModel()
) {
  Column {
    Text("dsad")
    Text("dsad ${vm.gdfgdf}")
    Button(onClick = {
      vm.startBillingConnection()
    }) {
      Text("start billing connection")
    }
  }
}
