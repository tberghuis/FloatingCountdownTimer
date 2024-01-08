package xyz.tberghuis.floatingtimer.tmp3

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import xyz.tberghuis.floatingtimer.LocalNavController

@Composable
fun Home(
  navController: NavHostController = LocalNavController.current,
  vm: HomeVm = viewModel()
) {
  val username by vm.usernameStateFlow.collectAsState()
  Column {
    Text("username: $username")
    Text("username: ${vm.password}")
    Button(onClick = {
      navController.navigate("login")
    }) {
      Text("login")
    }

    Button(onClick = {
      navController.navigate("tmp_change_color")
    }) {
      Text("change color")
    }


  }
}

class HomeVm(private val savedStateHandle: SavedStateHandle) : ViewModel() {
  val usernameStateFlow = savedStateHandle.getStateFlow<String?>("username_key", null)
  fun setUsername(username: String) {
    savedStateHandle["username_key"] = username
  }

  var password by mutableStateOf("123456")


}