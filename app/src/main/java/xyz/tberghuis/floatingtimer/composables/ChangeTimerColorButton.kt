package xyz.tberghuis.floatingtimer.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import xyz.tberghuis.floatingtimer.LocalNavController
import xyz.tberghuis.floatingtimer.R

@Composable
fun ChangeTimerColorButton(route: String, haloColor: Color) {
  val nav = LocalNavController.current
  Row(
    Modifier.clickable {
      nav.navigate(route)
    }
  ) {
    Box(
      Modifier
        .size(50.dp)
        .background(haloColor)
    )
    Image(
      painterResource(R.drawable.palette_icon),
      contentDescription = "",
    )
  }
}