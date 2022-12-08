package xyz.tberghuis.floatingtimer.tmp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun GradientDemo() {


  val gradient = Brush.verticalGradient(0f to Color.White, .3f to Color.Black)

  Column(verticalArrangement = Arrangement.Bottom) {
    Text("demo")
    Row(
      Modifier
        .height(100.dp)
        .fillMaxWidth()

        .background(gradient, alpha = .8f)
    ) {
      Text("trash")
    }
  }
}


