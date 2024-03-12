package xyz.tberghuis.floatingtimer.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import xyz.tberghuis.floatingtimer.ARC_WIDTH_NO_SCALE
import xyz.tberghuis.floatingtimer.TIMER_FONT_SIZE_NO_SCALE
import xyz.tberghuis.floatingtimer.TIMER_SIZE_NO_SCALE
import xyz.tberghuis.floatingtimer.data.SavedCountdown
import xyz.tberghuis.floatingtimer.data.SavedStopwatch
import xyz.tberghuis.floatingtimer.data.SavedTimer
import xyz.tberghuis.floatingtimer.service.countdown.CountdownViewDisplay
import xyz.tberghuis.floatingtimer.service.stopwatch.StopwatchView
import xyz.tberghuis.floatingtimer.tmp5.SettingsTimerPreviewVmc

@OptIn(ExperimentalLayoutApi::class, ExperimentalFoundationApi::class)
@Composable
fun <T : SavedTimer> ColumnScope.SavedTimersCard(
  savedTimers: List<T>,
  timerOnClick: (T) -> Unit,
  timerOnLongClick: (T) -> Unit,
) {
  ElevatedCard(
    modifier = Modifier
      .fillMaxWidth()
      .padding(15.dp),
  ) {
    Text(
      "Saved",
      modifier = Modifier
        .padding(10.dp)
        .align(Alignment.CenterHorizontally),
      fontSize = 20.sp,
      textAlign = TextAlign.Center
    )

    FlowRow(
      modifier = Modifier
        .padding(10.dp)
        .fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(10.dp, alignment = Alignment.CenterHorizontally),
      verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
      savedTimers.forEach { savedTimer ->
        val c = Color(savedTimer.timerColor)
        val settingsTimerPreviewVmc = SettingsTimerPreviewVmc(0f, c, "circle")
        Box(
          modifier = Modifier
            .combinedClickable(
              onClick = { timerOnClick(savedTimer) },
              onLongClick = { timerOnLongClick(savedTimer) },
            ),
        ) {
          when (savedTimer) {
            is SavedCountdown -> {
              CountdownViewDisplay(settingsTimerPreviewVmc, 1f, savedTimer.durationSeconds, false)
            }

            is SavedStopwatch -> {
              StopwatchView(
                isRunningStateFlow = null,
                bubbleSizeDp = TIMER_SIZE_NO_SCALE,
                arcWidth = ARC_WIDTH_NO_SCALE,
                haloColor = Color(savedTimer.timerColor),
                timeElapsed = 0,
                fontSize = TIMER_FONT_SIZE_NO_SCALE
              )
            }
          }
        }
      }
    }
  }
}