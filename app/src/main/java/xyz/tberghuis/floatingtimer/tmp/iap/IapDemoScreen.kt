package xyz.tberghuis.floatingtimer.tmp.iap

import android.app.Activity
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun IapDemoScreen(
  vm: IapDemoVm = viewModel()
) {
  val context = LocalContext.current
  Column {
    Button(onClick = {
      vm.startBillingConnection()
    }) {
      Text("start billing connection")
    }
    Button(onClick = {
      vm.queryProductDetails()
    }) {
      Text("query Product Details")
    }
    Button(onClick = {
      vm.queryPurchases()
    }) {
      Text("query Purchases")
    }
    Button(onClick = {
      vm.launchBillingFlow(context as Activity)
    }) {
      Text("launch Billing Flow")
    }
    Button(onClick = {
      vm.terminateBillingConnection()
    }) {
      Text("terminate Billing Connection")
    }
    Text("---------------------------")
  }
}
