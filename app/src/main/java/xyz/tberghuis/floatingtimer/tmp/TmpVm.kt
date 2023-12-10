package xyz.tberghuis.floatingtimer.tmp

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class TmpVm(private val application: Application) : AndroidViewModel(application) {
  var timerSizeScaleFactor by mutableFloatStateOf(0f) // 0<=x<=1

  val isRunningStateFlow = MutableStateFlow(false)
}