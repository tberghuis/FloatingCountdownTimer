package xyz.tberghuis.floatingtimer.tmp7

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
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
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import xyz.tberghuis.floatingtimer.TRASH_SIZE_DP
import xyz.tberghuis.floatingtimer.composables.LocalFloatingService
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.service.calcTimerIsHoverTrash

@Composable
fun Trash() {
  val service = LocalFloatingService.current
  var trashRect by remember { mutableStateOf(Rect.Zero) }

//  val iconTint by remember {
//    derivedStateOf {
//      if (isTimerDragHoveringTrash.value) {
//        Color.Red
//      } else {
//        Color.Black
//      }
//    }
//  }

  val iconTint = Color.Black

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
}
