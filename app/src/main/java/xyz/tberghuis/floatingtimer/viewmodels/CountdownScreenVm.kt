package xyz.tberghuis.floatingtimer.viewmodels

import android.app.Application
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import xyz.tberghuis.floatingtimer.DEFAULT_HALO_COLOR
import xyz.tberghuis.floatingtimer.R
import xyz.tberghuis.floatingtimer.composables.BackgroundTransCheckboxVm
import xyz.tberghuis.floatingtimer.data.SavedCountdown
import xyz.tberghuis.floatingtimer.data.SavedTimer
import xyz.tberghuis.floatingtimer.data.appDatabase
import xyz.tberghuis.floatingtimer.data.preferencesRepository
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.service.boundFloatingServiceProvider

class CountdownScreenVm(
  private val application: Application,
) : AndroidViewModel(application), TimerShapeChoiceVm, BackgroundTransCheckboxVm {
  private val savedCountdownDao = application.appDatabase.savedCountdownDao()
  val savedTimerDialogVmc = SavedTimerDialogVmc(application, viewModelScope)

  private val preferencesRepository = application.preferencesRepository

  val currentRingtoneVmc =
    CurrentRingtoneVmc(preferencesRepository.alarmRingtoneUriFlow, viewModelScope, application)

  val vibrationFlow = preferencesRepository.vibrationFlow

  val hours = mutableStateOf(TextFieldValue("0"))
  val minutes = mutableStateOf(TextFieldValue("0"))
  val seconds = mutableStateOf(TextFieldValue("0"))

  val snackbarHostState = SnackbarHostState()

  // future.txt refactor premiumVmc into sharedVm
  val premiumVmc = PremiumVmc(application, viewModelScope)
  private val boundFloatingService = application.boundFloatingServiceProvider

  var haloColor by mutableStateOf(DEFAULT_HALO_COLOR)

  override var timerShape by mutableStateOf("circle")
  override var label by mutableStateOf("")

  override var isBackgroundTransparent by mutableStateOf(false)

  init {
    viewModelScope.launch {
      preferencesRepository.haloColourFlow.collect {
        haloColor = it
      }
    }
  }

  fun savedCountdownFlow(): Flow<List<SavedCountdown>> {
    return savedCountdownDao.getAll()
  }

  fun updateVibration(vibration: Boolean) {
    logd("updateVibration $vibration")
    viewModelScope.launch {
      preferencesRepository.updateVibration(vibration)
    }
  }

  val soundFlow = preferencesRepository.soundFlow
  fun updateSound(sound: Boolean) {
    viewModelScope.launch {
      preferencesRepository.updateSound(sound)
    }
  }

  fun countdownButtonClick() {
    val totalSecs = calcTotalDurationSeconds() ?: return
    // no warning about var name shadowed???
    val label = if (timerShape == "label") label else null
    logd("countdownButtonClick isBackgroundTransparent $isBackgroundTransparent")
    addCountdown(totalSecs, haloColor, timerShape, label, isBackgroundTransparent)
  }

  private fun addCountdown(
    totalSecs: Int,
    haloColor: Color,
    timerShape: String,
    label: String?,
    isBackgroundTransparent: Boolean,
    savedTimer: SavedTimer? = null
  ) {
    viewModelScope.launch(IO) {
      if (shouldShowPremiumDialogMultipleTimers(application)) {
        withContext(Main) {
          premiumVmc.showPurchaseDialog = true
        }
        return@launch
      }
      val autoStart = preferencesRepository.autoStartFlow.first()
      boundFloatingService.provideService().overlayController.addCountdown(
        durationSeconds = totalSecs,
        haloColor = haloColor,
        timerShape = timerShape,
        label = label,
        isBackgroundTransparent = isBackgroundTransparent,
        start = autoStart,
        savedTimer = savedTimer,
      )
    }
  }

  fun savedCountdownClick(timer: SavedCountdown) {
    addCountdown(
      timer.durationSeconds,
      Color(timer.timerColor),
      timer.timerShape,
      timer.label,
      timer.isBackgroundTransparent,
      timer
    )
  }

  private fun calcTotalDurationSeconds(): Int? {
    val h: Int
    val min: Int
    val sec: Int
    try {
      h = hours.value.text.toInt()
      min = minutes.value.text.toInt()
      sec = seconds.value.text.toInt()
    } catch (e: NumberFormatException) {
      showSnackbar(application.resources.getString(R.string.invalid_countdown_duration))
      return null
    }
    val totalSecs = (h * 3600) + (min * 60) + sec
    if (totalSecs == 0) {
      showSnackbar(application.resources.getString(R.string.invalid_countdown_duration))
      return null
    }
    return totalSecs
  }

  private fun showSnackbar(message: String) {
    viewModelScope.launch {
      snackbarHostState.showSnackbar(
        message
      )
    }
  }

  fun addToSaved() {
    val durationSeconds = calcTotalDurationSeconds() ?: return
    val label = if (label == "" || timerShape != "label") null else label
    val timer = SavedCountdown(
      timerShape = timerShape,
      timerColor = haloColor.toArgb(),
      durationSeconds = durationSeconds,
      label = label,
      isBackgroundTransparent = isBackgroundTransparent
    )
    viewModelScope.launch(IO) {
      savedCountdownDao.insertAll(timer)
    }
  }
}