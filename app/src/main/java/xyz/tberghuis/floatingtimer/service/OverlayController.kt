package xyz.tberghuis.floatingtimer.service

import kotlinx.coroutines.flow.Flow
import xyz.tberghuis.floatingtimer.service.countdown.CountdownState

class OverlayController(val service: FloatingService) {

  val countdownState = CountdownState()
  private val countdownIsVisible: Flow<Boolean?>
    get() = countdownState.overlayState.isVisible

}