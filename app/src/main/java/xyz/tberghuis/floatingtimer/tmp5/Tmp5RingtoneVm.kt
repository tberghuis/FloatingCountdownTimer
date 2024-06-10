package xyz.tberghuis.floatingtimer.tmp5

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.data.dataStore

class Tmp5RingtoneVm(private val application: Application) : AndroidViewModel(application) {
  // todo get singleton from MainApplication
  private val prefRepo = Tmp5PrefRepo(application.dataStore)
  val ringtonePreviewVmc = TmpRingtonePreviewVmc(application)
  val currentRingtoneVmc =
    TmpCurrentRingtoneVmc(prefRepo.alarmRingtoneUriFlow, viewModelScope, application)

  val alarmList = RingtoneListController(application, TmpRingtoneType.ALARM)
  val ringtoneList = RingtoneListController(application, TmpRingtoneType.RINGTONE)
  val notificationList = RingtoneListController(application, TmpRingtoneType.NOTIFICATION)

  init {
  }

  fun setRingtone(uri: String) {
    viewModelScope.launch {
      prefRepo.updateAlarmRingtoneUri(uri)
    }
  }
}
