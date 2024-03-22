package xyz.tberghuis.floatingtimer.tmp4

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import xyz.tberghuis.floatingtimer.logd

class TmpLabelScreenVm(
  private val application: Application,
//  private val state: SavedStateHandle
) : AndroidViewModel(application) {
  fun createOverlay() {
    logd("createOverlay")
  }

}