package xyz.tberghuis.floatingtimer.tmp5

import androidx.compose.material3.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.Composable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import xyz.tberghuis.floatingtimer.composables.CountdownProgressLine
import xyz.tberghuis.floatingtimer.composables.runOnceOnGloballyPositioned
import xyz.tberghuis.floatingtimer.tmp4.OutlinedTextWithShadow
import xyz.tberghuis.floatingtimer.tmp4.TmpTimeDisplay


@Composable
fun TimerText(
  text: String,
  fontSize: TextUnit,
  isBackgroundTransparent: Boolean
) {
  CompositionLocalProvider(
    LocalDensity provides Density(LocalDensity.current.density, 1f)
  ) {
    if (isBackgroundTransparent) {
      OutlinedTextWithShadow(text, fontSize)
    } else {
      Text(
        text,
        fontSize = fontSize,
        fontFamily = FontFamily.Default,
        maxLines = 1,
        style = LocalTextStyle.current.copy(
          color = Color.Black,
          fontFeatureSettings = "tnum"
        ),
      )
    }
  }
}