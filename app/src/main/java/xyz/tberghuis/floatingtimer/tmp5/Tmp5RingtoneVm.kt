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

//  var previewRingtone: Ringtone? = null

  val currentRingtone = prefRepo.alarmRingtoneFlow


  init {
    // doitwrong
//    viewModelScope.launch {
//      prefRepo.alarmRingtoneUriFlow.collect { uri ->
//        val r = RingtoneManager.getRingtone(application, Uri.parse(uri))
//        currentRingtone.value = r
//      }
//    }
  }


  // shared flow, preview uri...
  // if playing same uri just stop

//  val ringtoneTitleFlow = prefRepo.alarmRingtoneUriFlow.map {
//    ringtoneTitleFromUri(application, it)
//  }

}
