package xyz.tberghuis.floatingtimer.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import kotlinx.coroutines.flow.collect
import xyz.tberghuis.floatingtimer.OverlayStateHolder.isTimerHoverTrash
import xyz.tberghuis.floatingtimer.OverlayStateHolder.timerOffset
import xyz.tberghuis.floatingtimer.TIMER_SIZE_DP
import xyz.tberghuis.floatingtimer.TRASH_SIZE_DP
import xyz.tberghuis.floatingtimer.logd

@Composable
fun Trash() {
  val context = LocalContext.current
  val timerSizePx = remember {
    val density = context.resources.displayMetrics.density
    TIMER_SIZE_DP * density
  }
  var trashRect by remember { mutableStateOf(Rect.Zero) }
  val isTimerDragHoveringTrash = remember {
    derivedStateOf {
      calcTimerIsHoverTrash(timerSizePx, trashRect)
    }
  }
  val iconTint by remember {
    derivedStateOf {
      if (isTimerDragHoveringTrash.value) {
        Color.Red
      } else {
        Color.Black
      }
    }
  }


  Box(
    Modifier
      .size(TRASH_SIZE_DP.dp)
      .clip(CircleShape)
      .background(Color.White.copy(alpha = .5f))
      .onGloballyPositioned {
        trashRect = it.boundsInRoot()
        logd("trashRect, $trashRect")
      },
    contentAlignment = Alignment.Center
  ) {


    Icon(
      Icons.Filled.Delete, "trash", modifier = Modifier
        .size(50.dp), tint = iconTint
    )
  }


// this is wack, but if it works...
  LaunchedEffect(isTimerDragHoveringTrash) {
    snapshotFlow {
      isTimerDragHoveringTrash.value
    }.collect {
      isTimerHoverTrash = it
    }
  }
}

fun calcTimerIsHoverTrash(timerSizePx: Float, trashRect: Rect): Boolean {
  val timerCenterX = timerOffset.x + (timerSizePx / 2)
  val timerCenterY = timerOffset.y + (timerSizePx / 2)
  if (
    timerCenterX < trashRect.left ||
    timerCenterX > trashRect.right ||
    timerCenterY < trashRect.top ||
    timerCenterY > trashRect.bottom
  ) {
    return false
  }
  return true
}