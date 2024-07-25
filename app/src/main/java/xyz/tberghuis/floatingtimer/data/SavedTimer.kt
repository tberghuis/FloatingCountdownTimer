package xyz.tberghuis.floatingtimer.data

interface SavedTimer {
  val id: Int


  val timerShape: String
  val timerColor: Int
  val label: String?
  val isBackgroundTransparent: Boolean

  val positionX: Int?
  val positionY: Int?


//  fun toSavedStopwatch(): SavedStopwatch {
//    return SavedStopwatch(
//      id = id,
//      timerShape = timerShape,
//      timerColor = timerColor,
//      label = label,
//      isBackgroundTransparent = isBackgroundTransparent,
//      positionX = positionX,
//      positionY = positionY
//    )
//  }

}