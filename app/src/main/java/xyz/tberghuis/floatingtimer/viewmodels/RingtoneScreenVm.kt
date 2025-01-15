package xyz.tberghuis.floatingtimer.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.data.RingtoneType
import xyz.tberghuis.floatingtimer.data.preferencesRepository

class RingtoneScreenVm(private val application: Application) : AndroidViewModel(application) {
  private val prefRepo = application.preferencesRepository
  val ringtonePreviewVmc = RingtonePreviewVmc(application, viewModelScope)
  val currentRingtoneVmc =
    CurrentRingtoneVmc(prefRepo.alarmRingtoneUriFlow, viewModelScope, application)

  val systemDefaultAlarmVmc = SystemDefaultAlarmVmc(application, viewModelScope)

  val alarmList = RingtoneListVmc(application, RingtoneType.ALARM)
  val ringtoneList = RingtoneListVmc(application, RingtoneType.RINGTONE)
  val notificationList = RingtoneListVmc(application, RingtoneType.NOTIFICATION)

  fun setRingtone(uri: String) {
    viewModelScope.launch {
      prefRepo.updateAlarmRingtoneUri(uri)
    }
  }

  override fun onCleared() {
    ringtonePreviewVmc.onCleared()
    super.onCleared()
  }
}
