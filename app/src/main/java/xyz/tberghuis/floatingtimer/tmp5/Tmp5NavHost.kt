package xyz.tberghuis.floatingtimer.tmp5

import android.app.Application
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import xyz.tberghuis.floatingtimer.LocalNavController

// nav for result demo

class TmpVm(private val application: Application, private val state: SavedStateHandle) :
  AndroidViewModel(application) {
  var navResult by mutableStateOf(Color.Red)
}

@Composable
fun <T> NavBackStackEntry.OnNavResult(savedStateHandleKey: String, onResult: (T) -> Unit) {
  LaunchedEffect(Unit) {
    val result = savedStateHandle.get<T>(savedStateHandleKey)
    result?.let {
      onResult(it)
      // ensure onResult only once (configuration change)
      savedStateHandle[savedStateHandleKey] = null
    }
  }
}

@Composable
fun Tmp5NavHost() {
  val navController = rememberNavController()
  CompositionLocalProvider(LocalNavController provides navController) {
    NavHost(
      navController = navController, startDestination = "start"
    ) {
      composable("start") { entry ->
        val vm: TmpVm = viewModel()
        entry.OnNavResult<Int>(savedStateHandleKey = "nav_result") { result ->
          vm.navResult = Color(result)
        }
        Column {
          Text("Start nav_result ${vm.navResult}")
          Button(onClick = {
            navController.navigate("change_color/for_result")
          }) {
            Text("nav change color")
          }
        }
      }
      composable("change_color/for_result") {
        Column {
          Text("change color")
          Button(onClick = {
            navController.previousBackStackEntry
              ?.savedStateHandle
              ?.set("nav_result", Color.Blue.toArgb())
            navController.popBackStack()
          }) {
            Text("pop with result")
          }
        }
      }
    }
  }
}