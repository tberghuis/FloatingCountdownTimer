package xyz.tberghuis.floatingtimer.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.providePreferencesRepository
import xyz.tberghuis.floatingtimer.tmp5.TmpCurrentRingtoneVmc
import xyz.tberghuis.floatingtimer.tmp5.TmpRingtonePreviewVmc
import xyz.tberghuis.floatingtimer.tmp5.TmpRingtoneType
import xyz.tberghuis.floatingtimer.tmp5.TmpSystemDefaultAlarmVmc

class RingtoneScreenVm(private val application: Application) : AndroidViewModel(application) {
  private val prefRepo = application.providePreferencesRepository()
  val ringtonePreviewVmc = TmpRingtonePreviewVmc(application)
  val currentRingtoneVmc =
    TmpCurrentRingtoneVmc(prefRepo.alarmRingtoneUriFlow, viewModelScope, application)

  val systemDefaultAlarmVmc = TmpSystemDefaultAlarmVmc(application)

  val alarmList = RingtoneListVmc(application, TmpRingtoneType.ALARM)
  val ringtoneList = RingtoneListVmc(application, TmpRingtoneType.RINGTONE)
  val notificationList = RingtoneListVmc(application, TmpRingtoneType.NOTIFICATION)

  fun setRingtone(uri: String) {
    viewModelScope.launch {
      prefRepo.updateAlarmRingtoneUri(uri)
    }
  }
}
