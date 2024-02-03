package xyz.tberghuis.floatingtimer.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import xyz.tberghuis.floatingtimer.LocalNavController
import xyz.tberghuis.floatingtimer.R

@Composable
fun ChangeTimerColorButton(route: String, haloColor: Color) {
  val nav = LocalNavController.current
  Box(
    Modifier
      .clickable {
        nav.navigate(route)
      }
      .size(50.dp),
    contentAlignment = Alignment.Center,
  ) {
    Canvas(
      Modifier.fillMaxSize()
    ) {
      drawArc(
        color = haloColor,
        startAngle = 0f,
        sweepAngle = 360f,
        useCenter = false,
        style = Stroke(10.dp.toPx()),
        size = Size(size.width, size.height)
      )
    }
    Image(
      painterResource(R.drawable.palette_icon),
      contentDescription = "",
      colorFilter = ColorFilter.tint(LocalContentColor.current)
    )
  }
}