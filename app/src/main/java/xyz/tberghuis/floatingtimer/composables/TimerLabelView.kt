package xyz.tberghuis.floatingtimer.composables

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import xyz.tberghuis.floatingtimer.common.TimeDisplay

@Composable
fun TimerLabelView(
  isPaused: Boolean,
  arcWidth: Dp,
  haloColor: Color,
  timeElapsed: Int,
  timeLeftFraction: Float,
  fontSize: TextUnit,
  label: String?,
  updateViewLayout: ((IntSize) -> Unit)? = null
) {
  @Suppress("NAME_SHADOWING") val label = if (label == "") null else label
  Box(
    modifier = Modifier
  ) {
    Box(
      modifier = Modifier
        .runOnceOnGloballyPositioned {
          updateViewLayout?.invoke(it.size)
        }
        .height(IntrinsicSize.Min)
        .width(IntrinsicSize.Min)
        .padding(5.dp)
        .graphicsLayer(
          shadowElevation = with(LocalDensity.current) { 5.dp.toPx() },
          shape = RoundedCornerShape(10.dp),
          clip = true
        )
        .background(Color.White),
      contentAlignment = Alignment.Center,
    ) {
      if (isPaused) {
        Icon(
          Icons.Filled.PlayArrow,
          contentDescription = "paused",
          modifier = Modifier.fillMaxSize(),
          tint = Color.LightGray
        )
      }
      Column(
        modifier = Modifier
          .width(IntrinsicSize.Max)
          .padding(5.dp)
      ) {
        Row(
          modifier = Modifier,
          // should this spacing scale???
//          horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
          label?.let {
            CompositionLocalProvider(
              LocalDensity provides Density(LocalDensity.current.density, 1f)
            ) {
              Text(
                "$label - ",
                fontSize = fontSize,
                fontFamily = FontFamily.Default,
                overflow = TextOverflow.Clip,
                maxLines = 1,
                style = LocalTextStyle.current.copy(
                  color = Color.Black,
                ),
              )
            }
          }
          TimeDisplay(timeElapsed, fontSize)
        }
        CountdownProgressLine(
          timeLeftFraction,
          arcWidth,
          haloColor
        )
      }
    }
  }
}