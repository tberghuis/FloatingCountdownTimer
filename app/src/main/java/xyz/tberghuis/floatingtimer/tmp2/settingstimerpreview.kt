package xyz.tberghuis.floatingtimer.tmp2

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.setValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import xyz.tberghuis.floatingtimer.R
import xyz.tberghuis.floatingtimer.composables.LocalHaloColour
import xyz.tberghuis.floatingtimer.service.BubbleProperties
import xyz.tberghuis.floatingtimer.service.countdown.CountdownViewDisplay

// Vmc = View Model Component
// todo initialHaloColour
class SettingsTimerPreviewVmc(initialScale: Float) : BubbleProperties {
  var bubbleSizeScaleFactor by mutableFloatStateOf(initialScale) // 0<=x<=1
  override val bubbleSizeDp by derivedStateOf {
    BubbleProperties.calcBubbleSizeDp(bubbleSizeScaleFactor)
  }
  override val arcWidth by derivedStateOf {
    BubbleProperties.calcArcWidth(bubbleSizeScaleFactor)
  }
  override val fontSize by derivedStateOf {
    BubbleProperties.calcFontSize(bubbleSizeScaleFactor)
  }
}

@Composable
fun SettingsTimerPreviewCard(vmc: SettingsTimerPreviewVmc) {
  ElevatedCard(
    modifier = Modifier
      .height(180.dp)
      .fillMaxWidth()
  ) {
    Row(
      modifier = Modifier
        .padding(10.dp)
        .fillMaxSize(),
      horizontalArrangement = Arrangement.Center,
      verticalAlignment = Alignment.CenterVertically,
    ) {
      Text(stringResource(id = R.string.preview), fontSize = 20.sp)
      Box(
        modifier = Modifier
          .width(140.dp),
        contentAlignment = Alignment.Center,
      ) {
        SettingsTimerPreviewBubble(vmc)
      }
    }
  }
}

@Composable
fun SettingsTimerPreviewBubble(vmc: SettingsTimerPreviewVmc) {
//  CompositionLocalProvider(LocalHaloColour provides MaterialTheme.colorScheme.primary) {
//  }

  CompositionLocalProvider(
    LocalDensity provides Density(
      LocalDensity.current.density,
      1f
    )
  ) {
    CountdownViewDisplay(vmc, 0.6f, 59)
  }
}