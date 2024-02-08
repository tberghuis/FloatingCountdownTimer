package xyz.tberghuis.floatingtimer.tmp4

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TmpBottomBar(currentScreen: TmpScreenType) {
  NavigationBar(

  ) {
    NavigationBarItem(
      selected = currentScreen is TmpScreenTypeCountdown,
      onClick = {},
      icon = {},
      modifier = Modifier,
      label = {
        Text(text = "Countdown")
      },
    )
    NavigationBarItem(
      selected = currentScreen is TmpScreenTypeStopwatch,
      onClick = {},
      icon = {},
      modifier = Modifier,
      label = {
        Text(text = "Stopwatch")
      },
    )
  }
}