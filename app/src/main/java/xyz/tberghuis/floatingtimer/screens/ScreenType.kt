package xyz.tberghuis.floatingtimer.screens

sealed interface ScreenType
data object ScreenTypeCountdown : ScreenType
data object ScreenTypeStopwatch : ScreenType
data object ScreenTypeClock : ScreenType