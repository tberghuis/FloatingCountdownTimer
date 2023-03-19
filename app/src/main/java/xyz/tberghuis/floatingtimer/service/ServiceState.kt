package xyz.tberghuis.floatingtimer.service

import xyz.tberghuis.floatingtimer.service.countdown.CountdownState


// place reactive state here

class ServiceState {

  val countdownState = CountdownState()

  var screenWidthPx = Int.MAX_VALUE
  var screenHeightPx = Int.MAX_VALUE

}