package xyz.tberghuis.floatingtimer.tmp.tmp01

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import xyz.tberghuis.floatingtimer.ui.theme.FloatingTimerTheme

class DeepLinkActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      FloatingTimerTheme {
        DeepLinkScreen()
      }
    }
  }
}