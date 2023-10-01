package xyz.tberghuis.floatingtimer.tmp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import xyz.tberghuis.floatingtimer.logd

class TmpVm(application: Application) : AndroidViewModel(application) {
  val fsdfsd = "fsdfsd"

  fun bindService() {
    logd("bindService")
  }
}