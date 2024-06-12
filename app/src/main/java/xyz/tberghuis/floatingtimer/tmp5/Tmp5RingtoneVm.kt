package xyz.tberghuis.floatingtimer.tmp5

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.data.dataStore
import xyz.tberghuis.floatingtimer.tmp7.TmpPreferencesRepository

class Tmp5RingtoneVm(private val application: Application) : AndroidViewModel(application) {
  // todo get singleton from MainApplication
  private val prefRepo = TmpPreferencesRepository(application.dataStore)
  val ringtonePreviewVmc = TmpRingtonePreviewVmc(application)
  val currentRingtoneVmc =
    TmpCurrentRingtoneVmc(prefRepo.alarmRingtoneUriFlow, viewModelScope, application)

  val systemDefaultAlarmVmc = TmpSystemDefaultAlarmVmc(application)

  val alarmList = RingtoneListVmc(application, TmpRingtoneType.ALARM)
  val ringtoneList = RingtoneListVmc(application, TmpRingtoneType.RINGTONE)
  val notificationList = RingtoneListVmc(application, TmpRingtoneType.NOTIFICATION)

  init {
  }

  fun setRingtone(uri: String) {
    viewModelScope.launch {
      prefRepo.updateAlarmRingtoneUri(uri)
    }
  }
}
