package xyz.tberghuis.floatingtimer.tmp3

sealed interface TmpScreenType
data object TmpScreenTypeHome : TmpScreenType
data object TmpScreenTypeSaved : TmpScreenType
data object TmpScreenTypeSettings : TmpScreenType
