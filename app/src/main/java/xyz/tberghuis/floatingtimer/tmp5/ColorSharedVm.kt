package xyz.tberghuis.floatingtimer.tmp5

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import xyz.tberghuis.floatingtimer.logd


class ColorSharedVm() : ViewModel() {


  val sharedFlow = MutableSharedFlow<String>()

  init {
    logd("ColorSharedVm init")
  }


  fun navigate(onResult: (String) -> Unit) {

  }
}