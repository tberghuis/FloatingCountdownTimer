package xyz.tberghuis.floatingtimer.tmp

import android.app.Application
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel

class TmpVm(private val application: Application) : AndroidViewModel(application) {
  val timerSizeFactor = mutableFloatStateOf(1f)

}