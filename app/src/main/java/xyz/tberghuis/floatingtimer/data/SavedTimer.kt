package xyz.tberghuis.floatingtimer.data

interface SavedTimer {
  val timerColor: Int
  val timerShape: String
  val label: String?
  val isBackgroundTransparent: Boolean
}