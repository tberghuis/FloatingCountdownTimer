package xyz.tberghuis.floatingtimer.tmp

import android.content.Context
import android.content.Context.VIBRATOR_SERVICE
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class VibrationVm(private val context: Context) {
  val vibrating = MutableStateFlow(false)

  val scope = CoroutineScope(Dispatchers.Default)

  private val vibrator = initVibrator()

  init {
    scope.launch {
      vibrating.collect {
        when (it) {
          true -> {
            vibrator.vibrate(
              VibrationEffect.createWaveform(
                longArrayOf(1500, 200),
                intArrayOf(255, 0),
                0
              )
            )
          }
          false -> {
            vibrator.cancel()
          }
        }
      }
    }
  }

  private fun initVibrator(): Vibrator {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
      val vibratorManager =
        context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
      vibratorManager.defaultVibrator
    } else {
      @Suppress("DEPRECATION") context.getSystemService(VIBRATOR_SERVICE) as Vibrator
    }
  }
}

@Composable
fun VibrationDemo() {
  val context = LocalContext.current

  // doitwrong
  val vibrationVm = remember { VibrationVm(context) }

  Column {
    Text("hello vibration")

    Button(onClick = {

      vibrationVm.vibrating.value = true

    }) {
      Text("start vibration")
    }

    Button(onClick = {
      vibrationVm.vibrating.value = false
    }) {
      Text("stop vibration")
    }

  }
}