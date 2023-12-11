package xyz.tberghuis.floatingtimer.tmp2

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel

class ChangeSizeViewModel(private val application: Application) : AndroidViewModel(application) {

  val settingsTimerPreviewVmc = SettingsTimerPreviewVmc()

  // todo datastore
  //  paywall dialog

}