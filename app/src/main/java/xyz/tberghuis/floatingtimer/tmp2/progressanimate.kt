package xyz.tberghuis.floatingtimer.tmp2

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ProgressAnimate() {

  var running by remember { mutableStateOf(false) }

  Column {
    MyCountdownIndicator(10000, 5000, running)
    Spacer(Modifier.height(10.dp))
    MyCountdownIndicator(10000, 5000, true)

    // todo doitwrong, just do the same way i do it in prod but have timeLeftMs update
    //  every 100ms, and use derivedStateOf for timeLeftSeconds
  }
}


// this is not a straight forward problem
@Composable
fun MyCountdownIndicator(totalTimeMs: Int, timeLeftMs: Int, running: Boolean) {
  var currentProgress by remember(
    totalTimeMs,
    timeLeftMs
  ) { mutableFloatStateOf(timeLeftMs.toFloat() / totalTimeMs) }
  if (!running) {
    LinearProgressIndicator(
      progress = currentProgress
    )
  } else {
    val animatedProgress by animateFloatAsState(
      targetValue = currentProgress,
      animationSpec = tween(durationMillis = timeLeftMs, easing = LinearEasing),
      label = "progress",
    )
    LinearProgressIndicator(
      progress = animatedProgress
    )

    LaunchedEffect(Unit) {
      currentProgress = 0f
    }
  }
}