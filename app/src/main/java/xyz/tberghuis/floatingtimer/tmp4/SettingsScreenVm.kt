package xyz.tberghuis.floatingtimer.tmp4

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.data.preferencesRepository
import xyz.tberghuis.floatingtimer.viewmodels.CurrentRingtoneVmc

class SettingsScreenVm(
  application: Application,
//  savedStateHandle: SavedStateHandle
) :
  AndroidViewModel(application) {
  private val prefs = application.preferencesRepository


  val currentRingtoneVmc =
    CurrentRingtoneVmc(prefs.alarmRingtoneUriFlow, viewModelScope, application)


  val loopingFlow = prefs.loopingFlow


  val haloColourFlow = prefs.haloColourFlow



  // can i do one way data flow?
  fun updateLooping(value: Boolean) {
    viewModelScope.launch {
      prefs.updateLooping(value)
    }
  }
}
