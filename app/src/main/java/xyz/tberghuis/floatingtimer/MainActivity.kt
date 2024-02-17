package xyz.tberghuis.floatingtimer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.iap.BillingClientWrapper
import xyz.tberghuis.floatingtimer.tmp4.TmpNavHost
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
          TmpNavHost()
        }
      }
    }
  }
}

val LocalNavController = compositionLocalOf<NavHostController> {
  error("CompositionLocal LocalNavController not present")
}