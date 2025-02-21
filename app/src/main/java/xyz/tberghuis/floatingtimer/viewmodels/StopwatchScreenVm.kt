package xyz.tberghuis.floatingtimer.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import xyz.tberghuis.floatingtimer.DEFAULT_HALO_COLOR
import xyz.tberghuis.floatingtimer.composables.BackgroundTransCheckboxVm
import xyz.tberghuis.floatingtimer.data.SavedStopwatch
import xyz.tberghuis.floatingtimer.data.SavedTimer
import xyz.tberghuis.floatingtimer.data.appDatabase
import xyz.tberghuis.floatingtimer.data.preferencesRepository
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.service.boundFloatingServiceProvider
import xyz.tberghuis.floatingtimer.tmp.tmp02.TmpSavedTimerDialogVmc

class StopwatchScreenVm(
  private val application: Application,
) : AndroidViewModel(application), TimerShapeChoiceVm, BackgroundTransCheckboxVm {
  private val savedStopwatchDao = application.appDatabase.savedStopwatchDao()

  //  var showDeleteDialog by mutableStateOf<SavedStopwatch?>(null)
  val savedTimerDialogVmc = TmpSavedTimerDialogVmc(application, viewModelScope)

  private val preferencesRepository = application.preferencesRepository
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

  fun savedStopwatchFlow(): Flow<List<SavedStopwatch>> {
    return savedStopwatchDao.getAll()
  }

  fun savedStopwatchClick(timer: SavedStopwatch) {
    addStopwatch(
      Color(timer.timerColor),
      timer.timerShape,
      timer.label,
      timer.isBackgroundTransparent,
      timer
    )
  }

  private fun addStopwatch(
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
      boundFloatingService.provideService().overlayController.addStopwatch(
        haloColor, timerShape, label, isBackgroundTransparent, savedTimer
      )
    }
  }

  fun stopwatchButtonClick() {
    val label = if (timerShape == "label") label else null
    logd("stopwatchButtonClick isBackgroundTransparent $isBackgroundTransparent")
    addStopwatch(haloColor, timerShape, label, isBackgroundTransparent)
  }

  fun addToSaved() {
    val label = if (label == "" || timerShape != "label") null else label
    val timer = SavedStopwatch(
      timerShape = timerShape,
      timerColor = haloColor.toArgb(),
      label = label,
      isBackgroundTransparent = isBackgroundTransparent
    )
    viewModelScope.launch(IO) {
      savedStopwatchDao.insertAll(timer)
    }
  }
}