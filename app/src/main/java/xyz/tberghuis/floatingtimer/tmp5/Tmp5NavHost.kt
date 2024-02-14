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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import xyz.tberghuis.floatingtimer.LocalNavController
import xyz.tberghuis.floatingtimer.logd

// nav for result demo
@Composable
fun Tmp5NavHost() {
  val navController = rememberNavController()
  CompositionLocalProvider(LocalNavController provides navController) {
    NavHost(
      navController = navController, startDestination = "start"
    ) {
      composable("start") { entry ->

        val vm: TmpVm = viewModel()

        LaunchedEffect(Unit) {
          logd("start LaunchedEffect")
          val nr = entry.savedStateHandle.get<Int>("nav_result")
          logd("get nav_result $nr")
          nr?.let {
            vm.navResult = Color(it)
            // ensure vm update only once (configuration change)
            entry.savedStateHandle["nav_result"] = null
          }
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




class TmpVm(private val application: Application, private val state: SavedStateHandle) :
  AndroidViewModel(application) {

//  var navResult by mutableStateOf("")
  var navResult by mutableStateOf(Color.Red)
}
