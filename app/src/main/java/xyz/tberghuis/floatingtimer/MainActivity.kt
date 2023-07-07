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
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.data.PreferencesRepository
import xyz.tberghuis.floatingtimer.iap.BillingClientWrapper
import xyz.tberghuis.floatingtimer.screens.ChangeColorScreen
import xyz.tberghuis.floatingtimer.screens.HomeScreen
import xyz.tberghuis.floatingtimer.screens.SettingsScreen
import xyz.tberghuis.floatingtimer.ui.theme.FloatingTimerTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  @Inject
  lateinit var preferencesRepository: PreferencesRepository

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    logd("onCreate")

    lifecycleScope.launch {
      BillingClientWrapper.run(application) {
        val purchased = it.checkHaloColourPurchased()
        logd("MainActivity onCreate purchased $purchased")
        preferencesRepository.updateHaloColourPurchased(purchased)
        if(!purchased){
          preferencesRepository.resetHaloColour()
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
      composable("settings") {
        SettingsScreen()
      }
      // future.txt as only one setting at the moment
//      composable("change_halo_colour") {
//        ChangeColorScreen()
//      }
    }
  }
}