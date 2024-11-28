package xyz.tberghuis.floatingtimer.tmp4

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.providePreferencesRepository
import xyz.tberghuis.floatingtimer.viewmodels.CurrentRingtoneVmc

class SettingsScreenVm(
  application: Application,
//  savedStateHandle: SavedStateHandle
) :
  AndroidViewModel(application) {
  private val prefs = application.providePreferencesRepository()


  val currentRingtoneVmc =
    CurrentRingtoneVmc(prefs.alarmRingtoneUriFlow, viewModelScope, application)


  val loopingFlow = prefs.loopingFlow

  // can i do one way data flow?
  fun updateLooping(value: Boolean) {
    viewModelScope.launch {
      prefs.updateLooping(value)
    }
  }
}
