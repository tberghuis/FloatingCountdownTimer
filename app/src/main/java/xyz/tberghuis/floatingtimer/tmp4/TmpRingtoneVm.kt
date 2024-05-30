package xyz.tberghuis.floatingtimer.tmp4

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import xyz.tberghuis.floatingtimer.logd

class TmpRingtoneVm(private val application: Application) : AndroidViewModel(application) {
  fun getRingtoneList() {
    logd("getRingtoneList")
  }
}