package xyz.tberghuis.floatingtimer.tmp4

import android.app.Application
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.DEFAULT_HALO_COLOR
import xyz.tberghuis.floatingtimer.R
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.provideDatabase
import xyz.tberghuis.floatingtimer.providePreferencesRepository
import xyz.tberghuis.floatingtimer.viewmodels.BoundFloatingServiceVmc
import xyz.tberghuis.floatingtimer.viewmodels.PremiumVmc

class TmpCountdownScreenVm(private val application: Application) : AndroidViewModel(application) {

  private val savedTimerDao = application.provideDatabase().tmpSavedTimerDao()

  // store savedTimer.id
  var showDeleteDialog by mutableStateOf<TmpSavedTimer?>(null)

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
      haloColor = preferencesRepository.haloColourFlow.first()
    }
  }

  fun savedTimerFlow(): Flow<List<TmpSavedTimer>> {
    return savedTimerDao.getAll()
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
    viewModelScope.launch {
      val min: Int
      val sec: Int
      try {
        min = minutes.value.text.toInt()
        sec = seconds.value.text.toInt()
      } catch (e: NumberFormatException) {
        // todo, use res string for message, translate
        snackbarHostState.showSnackbar(
          application.resources.getString(R.string.invalid_countdown_duration)
        )
        return@launch
      }
      val totalSecs = min * 60 + sec
      if (totalSecs == 0) {
        snackbarHostState.showSnackbar(
          application.resources.getString(R.string.invalid_countdown_duration)
        )
        return@launch
      }
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


  fun addToSaved() {
    tmp1()
  }

  fun tmp1() {
    val t1 = TmpSavedTimer(
      timerType = "countdown",
      timerShape = "circle",
      timerColor = "ffffffff"
    )
    viewModelScope.launch(IO) {
      savedTimerDao.insertAll(t1)
    }
  }

  fun tmp2() {
    viewModelScope.launch(IO) {
      val all = savedTimerDao.getAll()
      logd("timers all: $all")
    }
  }

  fun deleteSavedTimer(timer: TmpSavedTimer) {
    logd("deleteSavedTimer")
    viewModelScope.launch(IO) {
      savedTimerDao.delete(timer)
    }
  }
}
