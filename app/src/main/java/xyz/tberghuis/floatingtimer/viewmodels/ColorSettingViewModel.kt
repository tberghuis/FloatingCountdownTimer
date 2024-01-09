package xyz.tberghuis.floatingtimer.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.godaddy.android.colorpicker.HsvColor
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.DEFAULT_HALO_COLOR
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.providePreferencesRepository

class ColorSettingViewModel(application: Application, savedStateHandle: SavedStateHandle) :
  AndroidViewModel(application) {
  private val preferences = application.providePreferencesRepository()
  var colorPickerColorState = mutableStateOf(HsvColor.from(DEFAULT_HALO_COLOR))

  val premiumVmc = PremiumVmc(application, viewModelScope)

  private val premiumFlow = application.providePreferencesRepository().haloColourPurchasedFlow

  var initialised by mutableStateOf(false)
  lateinit var settingsTimerPreviewVmc: SettingsTimerPreviewVmc

  // null == default color
  val timerType: String? = savedStateHandle["timerType"]

  init {
    logd("ColorSettingViewModel timerType $timerType")

    viewModelScope.launch {
      val haloColor = preferences.haloColourFlow.first()
      colorPickerColorState.value = HsvColor.from(haloColor)
      val scale = preferences.bubbleScaleFlow.first()
      settingsTimerPreviewVmc = SettingsTimerPreviewVmc(scale, haloColor)
      initialised = true
    }
  }

  fun saveDefaultHaloColor() {
    viewModelScope.launch {
      preferences.updateHaloColour(colorPickerColorState.value.toColor())
    }
  }

  fun okButtonClick(ifPremiumCallback: () -> Unit) {
    viewModelScope.launch {
      logd("saveHaloColorClick")
      if (premiumFlow.first()) {
        ifPremiumCallback()
      } else {
        premiumVmc.showPurchaseDialog = true
      }
    }
  }
}