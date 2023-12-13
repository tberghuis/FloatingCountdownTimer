package xyz.tberghuis.floatingtimer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.iap.BillingClientWrapper
import xyz.tberghuis.floatingtimer.screens.ColorSettingScreen
import xyz.tberghuis.floatingtimer.screens.HomeScreen
import xyz.tberghuis.floatingtimer.tmp2.ChangeSizeScreen
import xyz.tberghuis.floatingtimer.ui.theme.FloatingTimerTheme

class MainActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    logd("onCreate")

    val preferencesRepository = application.providePreferencesRepository()
    lifecycleScope.launch {
      BillingClientWrapper.run(application) {
        val purchased = it.checkHaloColourPurchased()
        logd("MainActivity onCreate purchased $purchased")
        preferencesRepository.updateHaloColourPurchased(purchased)
        if (!purchased) {
          preferencesRepository.resetHaloColour()
          preferencesRepository.resetBubbleScale()
        }
      }
    }

    setContent {
      FloatingTimerTheme {
        Surface(
          modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        ) {
          FloatingTimerNavHost()
        }
      }
    }
  }
}

val LocalNavController = compositionLocalOf<NavHostController> {
  error("CompositionLocal LocalNavController not present")
}

@Composable
fun FloatingTimerNavHost(
) {
  val navController = rememberNavController()
  CompositionLocalProvider(LocalNavController provides navController) {
    NavHost(
      navController = navController, startDestination = "home"
    ) {
      composable("home") {
        HomeScreen()
      }
      composable("change_size") {
        ChangeSizeScreen()
      }
      composable("change_color") {
        ColorSettingScreen()
      }
    }
  }
}