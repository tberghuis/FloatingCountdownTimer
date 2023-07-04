package xyz.tberghuis.floatingtimer.tmp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import xyz.tberghuis.floatingtimer.di.SingletonModule.providePreferencesRepository
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.tmp.colorpicker.ColorPickerHomeScreen
import xyz.tberghuis.floatingtimer.tmp.colorpicker.ColorPickerScreen
import xyz.tberghuis.floatingtimer.tmp.godaddypicker.GodaddyPickerScreen
import xyz.tberghuis.floatingtimer.tmp.iap.IapDemoScreen
import xyz.tberghuis.floatingtimer.ui.theme.FloatingTimerTheme


val LocalHaloColour = compositionLocalOf<Color> {
  error("CompositionLocal LocalHaloColour not present")
}

class IapDemo : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val preferencesRepository = providePreferencesRepository(application)

    setContent {
      FloatingTimerTheme {

        val haloColour =
          preferencesRepository.haloColourFlow.collectAsState(initial = MaterialTheme.colorScheme.primary)

        LaunchedEffect(haloColour.value) {
          logd("LaunchedEffect haloColour: ${haloColour.value}")
        }

        CompositionLocalProvider(LocalHaloColour provides haloColour.value) {
          Surface(
            modifier = Modifier
              .fillMaxSize()
              .background(Color.White),
          ) {
            MyAppNavHost()
          }
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
      ColorPickerHomeScreen(navController)
    }
    composable("color_picker") {
      ColorPickerScreen()
    }

    composable("godaddy_picker") {
      GodaddyPickerScreen()
    }

  }
}


