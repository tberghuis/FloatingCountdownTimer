package xyz.tberghuis.floatingtimer.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.godaddy.android.colorpicker.HsvColor
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.providePreferencesRepository

class ColorSettingViewModel(application: Application) : AndroidViewModel(application) {
  private val preferences = application.providePreferencesRepository()
  var colorPickerColorState = mutableStateOf(HsvColor.from(Color.Blue))

  val premiumVmc = PremiumVmc(application, viewModelScope)

  private val premiumFlow = application.providePreferencesRepository().haloColourPurchasedFlow

  var initialised by mutableStateOf(false)
  lateinit var settingsTimerPreviewVmc: SettingsTimerPreviewVmc


  init {
    viewModelScope.launch {
      colorPickerColorState.value = HsvColor.from(preferences.haloColourFlow.first())
      val haloColor = preferences.haloColourFlow.first()
      val scale = preferences.bubbleScaleFlow.first()
      settingsTimerPreviewVmc = SettingsTimerPreviewVmc(scale, haloColor)
      initialised = true
    }
  }

  fun saveHaloColor() {
    viewModelScope.launch {
      preferences.updateHaloColour(colorPickerColorState.value.toColor())
    }
  }

  fun saveHaloColorClick() {
    viewModelScope.launch {
      logd("saveHaloColorClick")
      if (premiumFlow.first()) {
        saveHaloColor()
      } else {
        premiumVmc.showPurchaseDialog = true
      }
    }
  }
}
