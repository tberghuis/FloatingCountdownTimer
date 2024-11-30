package xyz.tberghuis.floatingtimer.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.data.preferencesRepository

class SettingsScreenVm(
  application: Application,
) :
  AndroidViewModel(application) {
  private val prefs = application.preferencesRepository

  val currentRingtoneVmc =
    CurrentRingtoneVmc(prefs.alarmRingtoneUriFlow, viewModelScope, application)

  val loopingFlow = prefs.loopingFlow
  val haloColourFlow = prefs.haloColourFlow
  val haloColourPurchasedFlow = prefs.haloColourPurchasedFlow

  fun updateLooping(value: Boolean) {
    viewModelScope.launch {
      prefs.updateLooping(value)
    }
  }
}