package xyz.tberghuis.floatingtimer.tmp.iaphalo

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel

// todo rewrite billingclientwrapper so only exposes flow
// of BillingClientEvent sealed class
// cancel flow calls endConnection?

@Composable
fun HaloScreen(
  vm: HaloScreenViewModel = viewModel()
) {
  Text("halo screen")

  Button(onClick = {}) {
    Text("change color")
  }

}