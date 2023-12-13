package xyz.tberghuis.floatingtimer.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.providePreferencesRepository
import xyz.tberghuis.floatingtimer.tmp2.SettingsTimerPreviewVmc

class SizeSettingViewModel(private val application: Application) : AndroidViewModel(application) {
  private val preferences = application.providePreferencesRepository()

  // doitwrong
  var initialised by mutableStateOf(false)
  lateinit var settingsTimerPreviewVmc: SettingsTimerPreviewVmc

  val premiumVmc = PremiumVmc(application, viewModelScope)
  private val premiumFlow = application.providePreferencesRepository().haloColourPurchasedFlow

  var previewHaloColor: Color? = null

  init {
    viewModelScope.launch {
      settingsTimerPreviewVmc = SettingsTimerPreviewVmc(preferences.bubbleScaleFlow.first())
      previewHaloColor = preferences.haloColourFlow.first()
      initialised = true
    }
  }

  // todo datastore
  //  paywall dialog

  fun saveChangeSize() {
    viewModelScope.launch {
      preferences.updateBubbleScale(settingsTimerPreviewVmc.bubbleSizeScaleFactor)
    }
  }

  // doitwrong, lets just repeat myself
  fun saveChangeSizeClick() {
    viewModelScope.launch {
      if (premiumFlow.first()) {
        saveChangeSize()
      } else {
        premiumVmc.showPurchaseDialog = true
      }
    }
  }


}