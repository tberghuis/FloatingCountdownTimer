package xyz.tberghuis.floatingtimer.service.countdown


sealed class TimerState {}

object TimerStateRunning : TimerState()
object TimerStatePaused : TimerState()
object TimerStateFinished : TimerState()
