package xyz.tberghuis.floatingtimer.service

import android.util.Log
import android.view.WindowManager
import androidx.compose.ui.graphics.Color
import kotlin.math.roundToInt
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.ARC_WIDTH_NO_SCALE
import xyz.tberghuis.floatingtimer.TIMER_FONT_SIZE_NO_SCALE
import xyz.tberghuis.floatingtimer.data.SavedCountdown
import xyz.tberghuis.floatingtimer.data.SavedStopwatch
import xyz.tberghuis.floatingtimer.data.SavedTimer
import xyz.tberghuis.floatingtimer.data.appDatabase
import xyz.tberghuis.floatingtimer.tmp4.TmpBubbleProperties
import xyz.tberghuis.floatingtimer.tmp4.TmpTimerViewHolder

//interface XxxBubbleProperties : TmpBubbleProperties {
//  val widthDp: Dp
//    get() = 0.dp
//  val heightDp: Dp
//    get() = 0.dp
//
//  companion object {
//    // todo remove
//    fun calcCountdownTimerSizeDp(scaleFactor: Float) =
//      60.dp * (scaleFactor + 1)
//
//    fun calcArcWidth(scaleFactor: Float) = ARC_WIDTH_NO_SCALE * (0.9f * scaleFactor + 1)
//    fun calcFontSize(scaleFactor: Float) = TIMER_FONT_SIZE_NO_SCALE * (1.2 * scaleFactor + 1)
//  }
//}

// future data class implements this, use composition over inheritance
// make update method a single method interface, does calcs and returns copy instance

// state that is static for lifetime of bubble goes here

abstract class Bubble(
  private val service: FloatingService,
  bubbleSizeScaleFactor: Float,
  override val haloColor: Color,
  override val timerShape: String,
  override val label: String? = null,
  override val isBackgroundTransparent: Boolean,
  private var savedTimer: SavedTimer? = null
) : TmpBubbleProperties {
  override val arcWidth = TmpBubbleProperties.calcArcWidth(bubbleSizeScaleFactor)
  override val fontSize = TmpBubbleProperties.calcFontSize(bubbleSizeScaleFactor)
  override val paddingTimerDisplay =
    TmpBubbleProperties.calcTimerDisplayPadding(bubbleSizeScaleFactor)

  val viewHolder: TmpTimerViewHolder

  init {
    // todo TmpTimerViewHolder
    // remove widthPx heightPx params
    viewHolder =
      TmpTimerViewHolder(service, savedTimer?.positionX, savedTimer?.positionY)
  }

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