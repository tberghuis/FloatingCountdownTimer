package xyz.tberghuis.floatingtimer.tmp3

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import xyz.tberghuis.floatingtimer.LocalNavController

@Composable
fun Login() {
  val navController = LocalNavController.current
  val homeVm: HomeVm = viewModel(navController.previousBackStackEntry!!)
  Column {
    Text("login screen")
    Button(onClick = {
      homeVm.setUsername("i_am_dope")
      navController.popBackStack()
    }) {
      Text("set username")
    }

    Button(onClick = {
      homeVm.password = "set password"
    }) {
      Text("set password")
    }
  }
}