package xyz.tberghuis.floatingtimer.tmp5

import android.app.Application
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.data.dataStore

class Tmp5RingtoneVm(private val application: Application) : AndroidViewModel(application) {
  // todo get singleton from MainApplication
  private val prefRepo = Tmp5PrefRepo(application, application.dataStore)

  val currentRingtoneFlow = prefRepo.alarmRingtoneFlow

  // shared flow, preview uri... (emit uri string when click event)
  // if playing same uri just stop
  // stop playing before start playing another ringtone
  // process in init block

}
