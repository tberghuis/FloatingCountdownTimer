package xyz.tberghuis.floatingtimer.viewmodels

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.MainActivity
import xyz.tberghuis.floatingtimer.R
import xyz.tberghuis.floatingtimer.data.appDatabase
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.service.boundFloatingServiceProvider

class DeepLinkScreenVm(
  private val application: Application,
) : AndroidViewModel(application) {
  private val savedStopwatchDao = application.appDatabase.savedStopwatchDao()
  private val savedCountdownDao = application.appDatabase.savedCountdownDao()

  var uiLink by mutableStateOf("")
  var uiTimerType by mutableStateOf("")
  var uiStart by mutableStateOf("")
  var uiResult by mutableStateOf("")

  fun processDataUri(data: Uri) {
    logd("data uri $data")

    val timerType = data.getQueryParameter("type")
    val id = data.getQueryParameter("id")
    val start = data.getBooleanQueryParameter("start", false)

    uiLink = data.toString()

    if (timerType == null || id == null) {
      uiResult = application.getString(R.string.invalid_link)
      return
    }

    uiTimerType = timerType
    uiStart = start.toString()

    viewModelScope.launch {
      if (shouldShowPremiumDialogMultipleTimers(application)) {
        uiResult = application.getString(R.string.need_premium_to_run_more_than_2_timers)
        return@launch
      }

      try {
        when (timerType) {
          "stopwatch" -> {
            addStopwatch(id.toInt(), start)
          }

          "countdown" -> {
            addCountdown(id.toInt(), start)
          }

          else -> {
            uiResult = application.getString(R.string.invalid_timer_type)
          }
        }
      } catch (e: RuntimeException) {
        uiResult = "error $e"
      }

      val numTimers =
        application.boundFloatingServiceProvider.provideService().overlayController.getNumberOfBubbles()
      if (numTimers == 0) {
        application.boundFloatingServiceProvider.provideService().stopSelf()
      }
    }
  }

  fun openFloatingTimer(activity: Activity) {
    val intent = Intent(activity, MainActivity::class.java)
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    activity.startActivity(intent)
  }

  private suspend fun addStopwatch(id: Int, start: Boolean) {
    val stopwatch = savedStopwatchDao.getById(id)

    if (stopwatch == null) {
      uiResult = "stopwatch timer id=$id not found"
      return
    }

    application.boundFloatingServiceProvider.provideService().overlayController.addStopwatch(
      haloColor = Color(stopwatch.timerColor),
      timerShape = stopwatch.timerShape,
      label = stopwatch.label,
      isBackgroundTransparent = stopwatch.isBackgroundTransparent,
      savedTimer = stopwatch,
      start = start
    )

    uiResult = application.getString(R.string.stopwatch_timer_launched)
  }

  private suspend fun addCountdown(id: Int, start: Boolean) {
    val countdown = savedCountdownDao.getById(id)

    if (countdown == null) {
      uiResult = "countdown timer id=$id not found"
      return
    }

    application.boundFloatingServiceProvider.provideService().overlayController.addCountdown(
      durationSeconds = countdown.durationSeconds,
      haloColor = Color(countdown.timerColor),
      timerShape = countdown.timerShape,
      label = countdown.label,
      isBackgroundTransparent = countdown.isBackgroundTransparent,
      savedTimer = countdown,
      start = start
    )

    uiResult = application.getString(R.string.countdown_timer_launched)
  }
}