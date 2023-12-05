package xyz.tberghuis.floatingtimer.viewmodels

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.godaddy.android.colorpicker.HsvColor
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.providePreferencesRepository

class SettingsViewModel(application: Application) : AndroidViewModel(application) {
  private val preferences = application.providePreferencesRepository()
  var colorPickerColorState = mutableStateOf(HsvColor.from(Color.Blue))

  val premiumVmc = PremiumVmc(application, viewModelScope)

  private val premiumFlow = application.providePreferencesRepository().haloColourPurchasedFlow

  init {
    viewModelScope.launch {
      colorPickerColorState.value = HsvColor.from(preferences.haloColourFlow.first())
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