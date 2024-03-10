package xyz.tberghuis.floatingtimer.tmp

import android.app.Application
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.AndroidViewModel
import xyz.tberghuis.floatingtimer.viewmodels.SettingsTimerPreviewVmc

// why no compiler error "application" ??? was happening in NoteBoat
class TmpVm(private val application: Application) : AndroidViewModel(application) {

  val haloColor = Color.Green
//  val settingsTimerPreviewVmc = SettingsTimerPreviewVmc(0f, haloColor)
  val settingsTimerPreviewVmc = SettingsTimerPreviewVmc(1f, haloColor)

}