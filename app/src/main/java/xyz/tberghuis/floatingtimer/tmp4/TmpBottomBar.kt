package xyz.tberghuis.floatingtimer.tmp4

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import xyz.tberghuis.floatingtimer.LocalNavController

@Composable
fun TmpBottomBar(currentScreen: TmpScreenType) {
  val nav = LocalNavController.current
  NavigationBar(

  ) {
    NavigationBarItem(
      selected = currentScreen is TmpScreenTypeCountdown,
      onClick = {
        if (currentScreen is TmpScreenTypeCountdown) {
          return@NavigationBarItem
        }
        nav.navigate("countdown")
      },
      icon = {
        Icon(Icons.Default.Timer, contentDescription = "Countdown")
      },
      modifier = Modifier,
      label = {
        Text(text = "Countdown")
      },
    )
    NavigationBarItem(
      selected = currentScreen is TmpScreenTypeStopwatch,
      onClick = {
        if (currentScreen is TmpScreenTypeStopwatch) {
          return@NavigationBarItem
        }
        nav.navigate("stopwatch")
      },
      icon = {
        Icon(Icons.Default.Timer, contentDescription = "Stopwatch")
      },
      modifier = Modifier,
      label = {
        Text(text = "Stopwatch")
      },
    )
  }
}