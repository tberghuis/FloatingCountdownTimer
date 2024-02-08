package xyz.tberghuis.floatingtimer.tmp4

sealed interface TmpScreenType
data object TmpScreenTypeCountdown : TmpScreenType
data object TmpScreenTypeStopwatch : TmpScreenType
data object TmpScreenTypeClock : TmpScreenType
