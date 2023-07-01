package xyz.tberghuis.floatingtimer.tmp.iaphalo

import android.app.Activity
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel

// todo rewrite billingclientwrapper so only exposes flow
// of BillingClientEvent sealed class
// cancel flow calls endConnection?

@Composable
fun HaloScreen(
  vm: HaloScreenViewModel = viewModel()
) {
  val context = LocalContext.current
  val purchased = vm.haloColourPurchasedFlow.collectAsState(initial = false)

  Text("halo screen")
  Text("purchased: ${purchased.value}")

  Button(onClick = {
    vm.checkHaloColourPurchased()
  }) {
    Text("check Halo Colour Purchased")
  }


  Button(onClick = {
    vm.purchaseHaloColourChange(context as Activity)
  }) {
    Text("purchase change halo color")
  }


}