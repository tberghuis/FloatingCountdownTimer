package xyz.tberghuis.floatingtimer

import android.content.Context
import android.content.Context.INPUT_SERVICE
import android.graphics.PixelFormat
import android.hardware.input.InputManager
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.view.WindowManager
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import xyz.tberghuis.floatingtimer.events.onClickClickTargetOverlay
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt
import xyz.tberghuis.floatingtimer.countdown.CountdownStateVFDVDFV

class OverlayComponent(
  private val context: Context,
  private val countdownOverlayState: CountdownStateVFDVDFV,
  private val stopService: () -> Unit
) {



}