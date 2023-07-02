package xyz.tberghuis.floatingtimer.tmp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import xyz.tberghuis.floatingtimer.tmp.colorpicker.ColorPickerHomeScreen
import xyz.tberghuis.floatingtimer.tmp.colorpicker.ColorPickerScreen
import xyz.tberghuis.floatingtimer.tmp.iap.IapDemoScreen
import xyz.tberghuis.floatingtimer.ui.theme.FloatingTimerTheme


class IapDemo : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      FloatingTimerTheme {
        Surface(
          modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        ) {
//          IapDemoScreen()
          MyAppNavHost()
        }
      }
    }


  }
}


@Composable
fun MyAppNavHost(
) {
  val navController = rememberNavController()
  NavHost(
    navController = navController, startDestination = "home"
  ) {
    composable("home") {
      ColorPickerHomeScreen {
        navController.navigate("color_picker")
      }
    }
    composable("color_picker") {
      ColorPickerScreen()
    }
  }
}


