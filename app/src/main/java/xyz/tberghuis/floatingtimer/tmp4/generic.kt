package xyz.tberghuis.floatingtimer.tmp4

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.first
import xyz.tberghuis.floatingtimer.LocalNavController


// TODO make generic
@Composable
fun NavHostController.UseSavedStateForResult(
  key: String,
  onResult: (String?) -> Unit
) {
  val stateFlow = currentBackStackEntry!!.savedStateHandle.getStateFlow<String?>(
    key,
    null
  )

  LaunchedEffect(Unit) {
    while (true) {
      stateFlow.first()
      val result = stateFlow.first()
      currentBackStackEntry!!.savedStateHandle[key] = null
      onResult(result)
    }
  }
}


// TODO make generic
fun NavHostController.popWithResult(
  key: String,
  value: String
) {
  previousBackStackEntry!!.savedStateHandle[key] = value
  popBackStack()
}


@Composable
fun Tmp4Home(
  navController: NavHostController = LocalNavController.current,
) {
  var myColor: String? by remember {
    mutableStateOf(null)
  }
  navController.UseSavedStateForResult("MY_COLOR") {
    myColor = it
  }

  Column {
    Text("myColor $myColor")
    Button(onClick = {
      navController.navigate("change_color")
    }) {
      Text("nav for result")
    }
  }
}

@Composable
fun Tmp4SetColor(
  navController: NavHostController = LocalNavController.current,
) {

  Column {
    Text("tmp change color")
    Button(onClick = {
      navController.popWithResult("MY_COLOR", "will mycolor blend")
    }) {
      Text("button")
    }
  }

}
