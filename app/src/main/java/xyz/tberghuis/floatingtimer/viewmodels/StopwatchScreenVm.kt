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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.DEFAULT_HALO_COLOR
import xyz.tberghuis.floatingtimer.MainApplication
import xyz.tberghuis.floatingtimer.composables.BackgroundTransCheckboxVm
import xyz.tberghuis.floatingtimer.data.SavedStopwatch
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.provideDatabase
import xyz.tberghuis.floatingtimer.providePreferencesRepository

class StopwatchScreenVm(
  private val application: Application,
) : AndroidViewModel(application), TimerShapeChoiceVm, BackgroundTransCheckboxVm {
  private val savedStopwatchDao = application.provideDatabase().savedStopwatchDao()
  var showDeleteDialog by mutableStateOf<SavedStopwatch?>(null)

  private val preferencesRepository = application.providePreferencesRepository()
  val premiumVmc = PremiumVmc(application, viewModelScope)
  private val boundFloatingService = (application as MainApplication).boundFloatingService

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
      timer.isBackgroundTransparent
    )
  }

  private fun addStopwatch(
    haloColor: Color,
    timerShape: String,
    label: String?,
    isBackgroundTransparent: Boolean
  ) {
    viewModelScope.launch {
      if (shouldShowPremiumDialogMultipleTimers(application)) {
        premiumVmc.showPurchaseDialog = true
        return@launch
      }
      boundFloatingService.provideFloatingService().overlayController.addStopwatch(
        haloColor, timerShape, label, isBackgroundTransparent
      )
    }
  }

  fun stopwatchButtonClick() {
    val label = if (timerShape == "label") label else null

    logd("stopwatchButtonClick isBackgroundTransparent $isBackgroundTransparent")

    addStopwatch(haloColor, timerShape, label, isBackgroundTransparent)
  }

  fun deleteSavedStopwatch(timer: SavedStopwatch) {
    viewModelScope.launch(IO) {
      savedStopwatchDao.delete(timer)
    }
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