package xyz.tberghuis.floatingtimer.tmp

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import xyz.tberghuis.floatingtimer.tmp2.ProgressAnimate

@Composable
fun TmpScreen(
  vm: TmpVm = viewModel()
) {
  Column(
    modifier = Modifier.padding(horizontal = 30.dp),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    Text("tmp screen")
    ProgressAnimate()
  }
}

val TMP_SIZE = 100.dp
val TMP_ARC_WIDTH = 10.dp

@Composable
fun TmpCountdown(progress: Float) {


  val sweepAngle = 360 * progress

  Box(
    modifier = Modifier
      .size(TMP_SIZE),
    contentAlignment = Alignment.Center
  ) {
    Canvas(
      Modifier.fillMaxSize()
    ) {
      drawArc(
        color = Color.Blue.copy(alpha = .1f),
        startAngle = 0f,
        sweepAngle = 360f,
        useCenter = false,
        style = Stroke(TMP_ARC_WIDTH.toPx()),
        size = Size(size.width, size.height)
      )

      drawArc(
        color = Color.Blue,
        -90f,
        sweepAngle,
        false,
        style = Stroke(TMP_ARC_WIDTH.toPx()),
        size = Size(size.width, size.height)
      )
    }

  }
}

