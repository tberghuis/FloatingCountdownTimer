package xyz.tberghuis.floatingtimer.tmp7

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTonalElevationEnabled
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import xyz.tberghuis.floatingtimer.common.TimeDisplay
import xyz.tberghuis.floatingtimer.service.stopwatch.Stopwatch
import xyz.tberghuis.floatingtimer.tmp4.CountdownProgressLine

@Composable
fun TmpStopwatchView(
  stopwatch: Stopwatch
) {
  Box(
    modifier = Modifier
      .size(stopwatch.widthDp, stopwatch.heightDp)
      .padding(5.dp),
    contentAlignment = Alignment.Center,
  ) {


    val absoluteElevation = LocalAbsoluteTonalElevation.current
    val color = MaterialTheme.colorScheme.surface
    CompositionLocalProvider(
      LocalContentColor provides contentColorFor(color),
      LocalAbsoluteTonalElevation provides absoluteElevation
    ) {

      Box(
        modifier = Modifier
          .surface(
            shape = RoundedCornerShape(10.dp),
            backgroundColor = surfaceColorAtElevation(
              color = color,
              elevation = absoluteElevation
            ),
            border = null,
            shadowElevation = with(LocalDensity.current) { 5.dp.toPx() }
          ),
        ) {
        Column(
          modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
          verticalArrangement = Arrangement.Center,
          horizontalAlignment = Alignment.CenterHorizontally,
        ) {

          TimeDisplay(59, stopwatch.fontSize)

          Box(
            modifier = Modifier.padding(
              start = 5.dp,
              end = 5.dp,
              bottom = 5.dp
            ),
            contentAlignment = Alignment.TopStart,
          ) {
            CountdownProgressLine(
              0.5f,
              stopwatch.arcWidth,
              stopwatch.haloColor
            )
          }
        }

      }
    }


  }
}


@Stable
private fun Modifier.surface(
  shape: Shape,
  backgroundColor: Color,
  border: BorderStroke?,
  shadowElevation: Float,
) = this
  .graphicsLayer(shadowElevation = shadowElevation, shape = shape, clip = false)
  .then(if (border != null) Modifier.border(border, shape) else Modifier)
  .background(color = backgroundColor, shape = shape)
  .clip(shape)

@Composable
private fun surfaceColorAtElevation(color: Color, elevation: Dp): Color =
  MaterialTheme.colorScheme.applyTonalElevation(color, elevation)


val LocalAbsoluteTonalElevation = compositionLocalOf { 0.dp }

@Composable
@ReadOnlyComposable
internal fun ColorScheme.applyTonalElevation(backgroundColor: Color, elevation: Dp): Color {
  val tonalElevationEnabled = LocalTonalElevationEnabled.current
  return if (backgroundColor == surface && tonalElevationEnabled) {
    surfaceColorAtElevation(elevation)
  } else {
    backgroundColor
  }
}