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
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.getSystemService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers


class VibrationVm {
  val scope = CoroutineScope(Dispatchers.Default)

  fun startVibration(context: Context) {


    val vib = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
      val vibratorManager =
        context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
      vibratorManager.defaultVibrator
    } else {
      @Suppress("DEPRECATION") context.getSystemService(VIBRATOR_SERVICE) as Vibrator
    }


//    VibrationEffect
    vib.vibrate(VibrationEffect.createOneShot(2000, VibrationEffect.DEFAULT_AMPLITUDE))
  }
}

val vibrationVm = VibrationVm()


@Composable
fun VibrationDemo() {
  val context = LocalContext.current
  Column {
    Text("hello vibration")

    Button(onClick = {

      vibrationVm.startVibration(context)

    }) {
      Text("start vibration")
    }

    Button(onClick = {}) {
      Text("stop vibration")
    }

  }
}