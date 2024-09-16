package xyz.tberghuis.floatingtimer.tmp7

import androidx.compose.ui.platform.ComposeView
import xyz.tberghuis.floatingtimer.logd

fun calcIsBubbleHoverTrash(
  timerView: ComposeView,
  trashView: ComposeView,
  timerWidthPx: Int,
  timerHeightPx: Int,
  trashWidthPx: Int,
  trashHeightPx: Int,
): Boolean {
  val timerLocation = IntArray(2)
  timerView.getLocationOnScreen(timerLocation)

  val trashLocation = IntArray(2)
  trashView.getLocationOnScreen(trashLocation)

  val timerCenterX = timerLocation[0] + (timerWidthPx / 2f)
  val timerCenterY = timerLocation[1] + (timerHeightPx / 2f)

  val trashLeft = trashLocation[0]
  val trashRight = trashLocation[0] + trashWidthPx
  val trashTop = trashLocation[1]
  val trashBottom = trashLocation[1] + trashHeightPx

  logd("timer center $timerCenterX $timerCenterY")
  logd("trash rect $trashLeft $trashRight $trashTop $trashBottom")

  return !(timerCenterX < trashLeft ||
      timerCenterX > trashRight ||
      timerCenterY < trashTop ||
      timerCenterY > trashBottom)
}
