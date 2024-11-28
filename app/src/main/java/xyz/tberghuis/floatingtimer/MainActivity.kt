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
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.data.preferencesRepository
import xyz.tberghuis.floatingtimer.ui.theme.FloatingTimerTheme

class MainActivity : ComponentActivity() {
  private fun checkPremium() {
    val preferencesRepository = application.preferencesRepository
    lifecycleScope.launch(IO) {
      val purchased =
        application.provideBillingClientWrapper().checkPremiumPurchased() ?: return@launch
      preferencesRepository.updateHaloColourPurchased(purchased)
      logd("MainActivity checkPremium purchased $purchased")
      if (!purchased) {
        preferencesRepository.resetHaloColour()
        preferencesRepository.resetBubbleScale()
      }
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    logd("onCreate")
    checkPremium()
    setContent {
      FloatingTimerTheme {
        Surface(
          modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        ) {
          FtNavHost()
        }
      }
    }
  }
}

val LocalNavController = compositionLocalOf<NavHostController> {
  error("CompositionLocal LocalNavController not present")
}