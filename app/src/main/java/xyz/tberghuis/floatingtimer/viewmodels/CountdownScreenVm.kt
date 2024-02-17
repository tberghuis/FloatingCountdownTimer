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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.DEFAULT_HALO_COLOR
import xyz.tberghuis.floatingtimer.MainApplication
import xyz.tberghuis.floatingtimer.R
import xyz.tberghuis.floatingtimer.data.SavedCountdown
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.provideDatabase
import xyz.tberghuis.floatingtimer.providePreferencesRepository

class CountdownScreenVm(
  private val application: Application,
//  private val state: SavedStateHandle
) : AndroidViewModel(application) {
  private val savedCountdownDao = application.provideDatabase().savedCountdownDao()
  var showDeleteDialog by mutableStateOf<SavedCountdown?>(null)

  private val preferencesRepository = application.providePreferencesRepository()
  val vibrationFlow = preferencesRepository.vibrationFlow
  var minutes = mutableStateOf(TextFieldValue("0"))
  var seconds = mutableStateOf(TextFieldValue("0"))

  val snackbarHostState = SnackbarHostState()
  // future.txt refactor premiumVmc into sharedVm
  val premiumVmc = PremiumVmc(application, viewModelScope)
  private val boundFloatingService = (application as MainApplication).boundFloatingService

  var haloColor by mutableStateOf(DEFAULT_HALO_COLOR)

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
    addCountdown(totalSecs, haloColor)
  }

  private fun addCountdown(totalSecs: Int, haloColor: Color) {
    viewModelScope.launch {
      if (shouldShowPremiumDialogMultipleTimers(application)) {
        premiumVmc.showPurchaseDialog = true
        return@launch
      }
      boundFloatingService.provideFloatingService().overlayController.addCountdown(
        totalSecs,
        haloColor
      )
    }
  }

  fun savedCountdownClick(timer: SavedCountdown) {
    addCountdown(timer.durationSeconds, Color(timer.timerColor))
  }

  private fun calcTotalDurationSeconds(): Int? {
    val min: Int
    val sec: Int
    try {
      min = minutes.value.text.toInt()
      sec = seconds.value.text.toInt()
    } catch (e: NumberFormatException) {
      showSnackbar(application.resources.getString(R.string.invalid_countdown_duration))
      return null
    }
    val totalSecs = min * 60 + sec
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
    val timer = SavedCountdown(
      timerShape = "circle",
      timerColor = haloColor.toArgb(),
      durationSeconds = durationSeconds
    )
    viewModelScope.launch(IO) {
      savedCountdownDao.insertAll(timer)
    }
  }

  fun deleteSavedCountdown(timer: SavedCountdown) {
    logd("deleteSavedTimer")
    viewModelScope.launch(IO) {
      savedCountdownDao.delete(timer)
    }
  }
}