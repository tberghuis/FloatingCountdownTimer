package xyz.tberghuis.floatingtimer.tmp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.tmp3.TmpNavHost
import xyz.tberghuis.floatingtimer.tmp3.TmpNavigateResult
import xyz.tberghuis.floatingtimer.tmp4.Tmp4NavHost
import xyz.tberghuis.floatingtimer.tmp5.Tmp5NavHost
import xyz.tberghuis.floatingtimer.ui.theme.FloatingTimerTheme

class TmpActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    logd("onCreate")

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