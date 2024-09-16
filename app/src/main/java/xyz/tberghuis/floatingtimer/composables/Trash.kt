package xyz.tberghuis.floatingtimer.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun Trash() {
  val service = LocalFloatingService.current
  val iconTint = if (service.overlayController.trashController.isBubbleHoveringTrash) {
    Color.Red
  } else {
    Color.Black
  }

  Box(
    Modifier
      .fillMaxSize()
      .clip(CircleShape)
      .background(Color.White.copy(alpha = .5f)),
    contentAlignment = Alignment.Center
  ) {
    Icon(
      Icons.Filled.Delete, "trash", modifier = Modifier
        .size(50.dp), tint = iconTint
    )
  }
}
