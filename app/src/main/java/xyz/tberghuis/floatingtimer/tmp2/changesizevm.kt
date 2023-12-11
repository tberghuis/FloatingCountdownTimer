package xyz.tberghuis.floatingtimer.tmp2

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel

class ChangeSizeViewModel(private val application: Application) : AndroidViewModel(application) {
  var timerSizeScaleFactor by mutableFloatStateOf(0f) // 0<=x<=1

  // todo datastore
  //  paywall dialog

}