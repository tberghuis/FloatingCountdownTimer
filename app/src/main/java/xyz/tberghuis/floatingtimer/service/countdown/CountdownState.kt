package xyz.tberghuis.floatingtimer.service.countdown

sealed class TimerState {}
data object TimerStateRunning : TimerState()
data object TimerStatePaused : TimerState()
data object TimerStateFinished : TimerState()