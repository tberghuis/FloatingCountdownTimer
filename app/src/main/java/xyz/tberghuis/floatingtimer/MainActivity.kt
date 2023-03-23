package xyz.tberghuis.floatingtimer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import dagger.hilt.android.AndroidEntryPoint
import xyz.tberghuis.floatingtimer.screens.HomeScreen
import xyz.tberghuis.floatingtimer.tmp.fragment.FragmentDemo
import xyz.tberghuis.floatingtimer.ui.theme.FloatingTimerTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
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
//          HomeScreen()
          FragmentDemo()
        }
      }
    }
  }
}