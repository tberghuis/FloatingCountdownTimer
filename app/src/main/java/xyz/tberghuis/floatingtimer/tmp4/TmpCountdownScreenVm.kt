package xyz.tberghuis.floatingtimer.tmp4

import android.app.Application
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.DEFAULT_HALO_COLOR
import xyz.tberghuis.floatingtimer.R
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.provideDatabase
import xyz.tberghuis.floatingtimer.providePreferencesRepository
import xyz.tberghuis.floatingtimer.viewmodels.BoundFloatingServiceVmc
import xyz.tberghuis.floatingtimer.viewmodels.PremiumVmc

class TmpCountdownScreenVm(
  private val application: Application,
//  private val state: SavedStateHandle
) : AndroidViewModel(application) {

  private val savedCountdownDao = application.provideDatabase().tmpSavedCountdownDao()

  // store savedTimer.id
  var showDeleteDialog by mutableStateOf<TmpSavedCountdown?>(null)

  private val preferencesRepository = application.providePreferencesRepository()
  val vibrationFlow = preferencesRepository.vibrationFlow
  var minutes = mutableStateOf(TextFieldValue("0"))
  var seconds = mutableStateOf(TextFieldValue("0"))

  // todo GrantOverlayVmc
  val grantOverlayVmc = TmpGrantOverlayVmc()

  val snackbarHostState = SnackbarHostState()
  val premiumVmc = PremiumVmc(application, viewModelScope)
  private val boundFloatingServiceVmc = BoundFloatingServiceVmc(application)

  var haloColor by mutableStateOf(DEFAULT_HALO_COLOR)

  init {
    viewModelScope.launch {
//      haloColor = preferencesRepository.haloColourFlow.first()
      preferencesRepository.haloColourFlow.collect {
        haloColor = it
      }
    }
  }

  fun savedCountdownFlow(): Flow<List<TmpSavedCountdown>> {
    return savedCountdownDao.getAll()
  }

  private suspend fun shouldShowPremiumDialog(): Boolean {
    val premiumPurchased =
      application.providePreferencesRepository().haloColourPurchasedFlow.first()
    val floatingService = boundFloatingServiceVmc.provideFloatingService()
    val numBubbles = floatingService.overlayController.getNumberOfBubbles()
    return !premiumPurchased && numBubbles == 2
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
    viewModelScope.launch {
      if (shouldShowPremiumDialog()) {
        premiumVmc.showPurchaseDialog = true
        return@launch
      }
      boundFloatingServiceVmc.provideFloatingService().overlayController.addCountdown(
        totalSecs,
        haloColor
      )
    }
  }

  fun savedCountdownClick(timer: TmpSavedCountdown) {
    viewModelScope.launch {
      if (shouldShowPremiumDialog()) {
        premiumVmc.showPurchaseDialog = true
        return@launch
      }
      boundFloatingServiceVmc.provideFloatingService().overlayController.addCountdown(
        timer.durationSeconds,
        Color(timer.timerColor)
      )
    }
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

  // doitwrong
  fun addToSaved() {
    val durationSeconds = calcTotalDurationSeconds() ?: return
    val timer = TmpSavedCountdown(
      timerShape = "circle",
      timerColor = haloColor.toArgb(),
      durationSeconds = durationSeconds
    )

    viewModelScope.launch(IO) {
      savedCountdownDao.insertAll(timer)
    }
  }


//  fun tmp2() {
//    viewModelScope.launch(IO) {
//      val all = savedTimerDao.getAll()
//      logd("timers all: $all")
//    }
//  }

  fun deleteSavedCountdown(timer: TmpSavedCountdown) {
    logd("deleteSavedTimer")
    viewModelScope.launch(IO) {
      savedCountdownDao.delete(timer)
    }
  }
}
