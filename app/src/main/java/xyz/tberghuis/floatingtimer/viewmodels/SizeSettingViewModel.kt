package xyz.tberghuis.floatingtimer.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.data.preferencesRepository
import xyz.tberghuis.floatingtimer.tmp5.TmpSettingsTimerPreviewVmc

class SizeSettingViewModel(application: Application) : AndroidViewModel(application) {
  private val preferences = application.preferencesRepository

  // doitwrong
  var initialised by mutableStateOf(false)
  lateinit var settingsTimerPreviewVmc: TmpSettingsTimerPreviewVmc

  val premiumVmc = PremiumVmc(application, viewModelScope)
  private val premiumFlow = application.preferencesRepository.haloColourPurchasedFlow

  init {
    viewModelScope.launch {
      val haloColor = preferences.haloColourFlow.first()
      val scale = preferences.bubbleScaleFlow.first()
      settingsTimerPreviewVmc = TmpSettingsTimerPreviewVmc(scale, haloColor, "circle", null, false)
      initialised = true
    }
  }

  fun saveDefaultSize() {
    viewModelScope.launch {
      preferences.updateBubbleScale(settingsTimerPreviewVmc.bubbleSizeScaleFactor)
    }
  }

  // move to premiumVmc
  fun okButtonClick(ifPremiumCallback: () -> Unit) {
    viewModelScope.launch {
         if (premiumFlow.first()) {
        ifPremiumCallback()
      } else {
        premiumVmc.showPurchaseDialog = true
      }
    }
  }
}