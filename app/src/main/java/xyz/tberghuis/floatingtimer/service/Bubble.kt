package xyz.tberghuis.floatingtimer.service

import android.util.Log
import android.view.WindowManager
import androidx.compose.ui.graphics.Color
import kotlin.math.roundToInt
import androidx.compose.ui.unit.Dp
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.data.SavedCountdown
import xyz.tberghuis.floatingtimer.data.SavedStopwatch
import xyz.tberghuis.floatingtimer.data.SavedTimer
import xyz.tberghuis.floatingtimer.data.appDatabase

abstract class Bubble(
  private val service: FloatingService,
  bubbleSizeScaleFactor: Float,
  override val haloColor: Color,
  override val timerShape: String,
  override val label: String? = null,
  override val isBackgroundTransparent: Boolean,
  private var savedTimer: SavedTimer? = null
) : BubbleProperties {
  override val arcWidth = BubbleProperties.calcArcWidth(bubbleSizeScaleFactor)
  override val fontSize = BubbleProperties.calcFontSize(bubbleSizeScaleFactor)
  override val paddingTimerDisplay =
    BubbleProperties.calcTimerDisplayPadding(bubbleSizeScaleFactor)

  val viewHolder = TimerViewHolder(service, savedTimer?.positionX, savedTimer?.positionY)

  open fun exit() {
    try {
      service.ftWindowManager.removeView(viewHolder.view)
    } catch (e: IllegalArgumentException) {
      Log.e("Bubble", "IllegalArgumentException $e")
    }
  }

  abstract fun reset()
  abstract fun onTap()

  fun saveTimerPositionIfNull() {
    savedTimer?.let {
      if (it.positionX == null)
        saveTimerPosition()
    }
  }

  fun saveTimerPosition() {
    service.scope.launch(IO) {
      savedTimer = savedTimer?.let {
        when (it) {
          is SavedStopwatch -> {
            it.copy(
              positionX = viewHolder.params.x,
              positionY = viewHolder.params.y
            ).also { savedStopwatch ->
              service.application.appDatabase.savedStopwatchDao().update(savedStopwatch)
            }
          }

          is SavedCountdown -> {
            it.copy(
              positionX = viewHolder.params.x,
              positionY = viewHolder.params.y
            ).also { savedCountdown ->
              service.application.appDatabase.savedCountdownDao().update(savedCountdown)
            }
          }

          else -> {
            it
          }
        }
      }
    }
  }
}

fun dimensionDpToPx(dp: Dp, density: Float): Int {
  if (dp == Dp.Unspecified) {
    return WindowManager.LayoutParams.MATCH_PARENT
  }
  return (dp.value * density).roundToInt()
}