package xyz.tberghuis.floatingtimer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import xyz.tberghuis.floatingtimer.data.PreferencesRepository
import xyz.tberghuis.floatingtimer.screens.HomeScreen
import xyz.tberghuis.floatingtimer.tmp.VibrationDemo
import xyz.tberghuis.floatingtimer.ui.theme.FloatingTimerTheme


val LocalHaloColour = compositionLocalOf<Color> {
  error("CompositionLocal LocalHaloColour not present")
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


  @Inject
  lateinit var preferencesRepository: PreferencesRepository


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    logd("onCreate")

//    val haloColourFlow = application.


    setContent {
      val haloColourLong =
        preferencesRepository.haloColourFlow.collectAsState(initial = MaterialTheme.colorScheme.primary.value.toLong())

      FloatingTimerTheme {
        val haloColour =
          if (haloColourLong.value == null) MaterialTheme.colorScheme.primary else Color(
            haloColourLong.value!!
          )

        CompositionLocalProvider(LocalHaloColour provides haloColour) {
          Surface(
            modifier = Modifier
              .fillMaxSize()
              .background(Color.White),
          ) {
            HomeScreen()
          }
        }
      }
    }
  }
}