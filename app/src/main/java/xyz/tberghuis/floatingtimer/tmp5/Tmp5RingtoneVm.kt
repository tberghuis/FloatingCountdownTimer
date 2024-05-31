package xyz.tberghuis.floatingtimer.tmp5

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import xyz.tberghuis.floatingtimer.data.dataStore

class Tmp5RingtoneVm(private val application: Application) : AndroidViewModel(application) {
  // todo get singleton from MainApplication
  private val prefRepo = Tmp5PrefRepo(application.dataStore)


}