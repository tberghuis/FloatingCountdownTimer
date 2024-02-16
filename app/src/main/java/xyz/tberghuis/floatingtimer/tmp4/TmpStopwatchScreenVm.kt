package xyz.tberghuis.floatingtimer.tmp4

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.DEFAULT_HALO_COLOR
import xyz.tberghuis.floatingtimer.MainApplication
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.provideDatabase
import xyz.tberghuis.floatingtimer.providePreferencesRepository
import xyz.tberghuis.floatingtimer.viewmodels.PremiumVmc

class TmpStopwatchScreenVm(
  private val application: Application,
) : AndroidViewModel(application) {
  private val savedStopwatchDao = application.provideDatabase().savedStopwatchDao()
  var showDeleteDialog by mutableStateOf<TmpSavedStopwatch?>(null)

  private val preferencesRepository = application.providePreferencesRepository()
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

  fun savedStopwatchFlow(): Flow<List<TmpSavedStopwatch>> {
    return savedStopwatchDao.getAll()
  }

  fun savedStopwatchClick(timer: TmpSavedStopwatch) {
    addStopwatch(Color(timer.timerColor))
  }

  private fun addStopwatch(haloColor: Color) {
    viewModelScope.launch {
      if (shouldShowPremiumDialogMultipleTimers(application)) {
        premiumVmc.showPurchaseDialog = true
        return@launch
      }
      boundFloatingService.provideFloatingService().overlayController.addStopwatch(
        haloColor
      )
    }
  }

  fun stopwatchButtonClick() {
    addStopwatch(haloColor)
  }

  fun deleteSavedStopwatch(timer: TmpSavedStopwatch) {
    viewModelScope.launch(IO) {
      savedStopwatchDao.delete(timer)
    }
  }
}