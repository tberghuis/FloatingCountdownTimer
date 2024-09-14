package xyz.tberghuis.floatingtimer.tmp6

import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.tmp4.TmpService

class TmpOverlayController(val service: TmpService) {
  init {
    logd("TmpOverlayController")
  }

  fun helloController() {
    logd("helloController")
  }

}