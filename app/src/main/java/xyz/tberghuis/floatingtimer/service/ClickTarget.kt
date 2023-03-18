package xyz.tberghuis.floatingtimer.service

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ClickTarget(
  onClick: () -> Unit
) {
  Box(modifier = Modifier
    .border(BorderStroke(2.dp, Color.Green))
    .clickable {
      onClick()
    }
  ) {
    Text("click target")
  }
}