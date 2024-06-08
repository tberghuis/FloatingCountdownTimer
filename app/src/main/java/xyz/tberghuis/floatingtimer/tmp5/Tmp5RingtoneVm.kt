package xyz.tberghuis.floatingtimer.tmp5

import android.app.Application
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.map
import xyz.tberghuis.floatingtimer.data.dataStore

class Tmp5RingtoneVm(private val application: Application) : AndroidViewModel(application) {
  // todo get singleton from MainApplication
  private val prefRepo = Tmp5PrefRepo(application.dataStore)

  var previewRingtone: Ringtone? = null

  // shared flow, preview uri...
  // if playing same uri just stop

  val ringtoneTitleFlow = prefRepo.alarmRingtoneUriFlow.map {
    ringtoneTitleFromUri(application, it)
  }

}

// doitwrong
fun ringtoneTitleFromUri(application: Application, uri: String): String {
  val r = RingtoneManager.getRingtone(application, Uri.parse(uri))
  return r?.getTitle(application) ?: "error: no title"
}
