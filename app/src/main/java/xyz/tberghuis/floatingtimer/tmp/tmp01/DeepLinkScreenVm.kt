package xyz.tberghuis.floatingtimer.tmp.tmp01

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
import xyz.tberghuis.floatingtimer.data.appDatabase
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.service.boundFloatingServiceProvider
import xyz.tberghuis.floatingtimer.viewmodels.shouldShowPremiumDialogMultipleTimers

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
      uiResult = "invalid link"
      return
    }

    uiTimerType = timerType
    uiStart = start.toString()

    viewModelScope.launch {
      if (shouldShowPremiumDialogMultipleTimers(application)) {
        uiResult = "need premium to run more than 2 timers"
        return@launch
      }

      when (timerType) {
        "stopwatch" -> {
          addStopwatch(id.toInt(), start)
        }

        "countdown" -> {
          addCountdown(id.toInt(), start)
        }

        else -> {
          uiResult = "invalid timer type"
          return@launch
        }
      }
      uiResult = "timer launched"
    }
  }

  fun openFloatingTimer(activity: Activity) {
    val intent = Intent(activity, MainActivity::class.java)
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    activity.startActivity(intent)
  }

  private suspend fun addStopwatch(id: Int, start: Boolean) {
    val sw = savedStopwatchDao.getById(id)
    application.boundFloatingServiceProvider.provideService().overlayController.addStopwatch(
      haloColor = Color(sw.timerColor),
      timerShape = sw.timerShape,
      label = sw.label,
      isBackgroundTransparent = sw.isBackgroundTransparent,
      savedTimer = sw,
      start = start
    )
  }

  private suspend fun addCountdown(id: Int, start: Boolean) {
    val countdown = savedCountdownDao.getById(id)
    application.boundFloatingServiceProvider.provideService().overlayController.addCountdown(
      durationSeconds = countdown.durationSeconds,
      haloColor = Color(countdown.timerColor),
      timerShape = countdown.timerShape,
      label = countdown.label,
      isBackgroundTransparent = countdown.isBackgroundTransparent,
      savedTimer = countdown,
      start = start
    )
  }
}