package xyz.tberghuis.floatingtimer.composables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import xyz.tberghuis.floatingtimer.LocalNavController
import xyz.tberghuis.floatingtimer.R
import xyz.tberghuis.floatingtimer.screens.ScreenType
import xyz.tberghuis.floatingtimer.screens.ScreenTypeCountdown
import xyz.tberghuis.floatingtimer.screens.ScreenTypeStopwatch

@Composable
fun FtBottomBar(currentScreen: ScreenType) {
  val nav = LocalNavController.current
  NavigationBar(

  ) {
    NavigationBarItem(
      selected = currentScreen is ScreenTypeCountdown,
      onClick = {
        if (currentScreen is ScreenTypeCountdown) {
          return@NavigationBarItem
        }
        nav.navigate("countdown")
      },
      icon = {
        Icon(Icons.Default.Timer, contentDescription = stringResource(R.string.countdown))
      },
      modifier = Modifier,
      label = {
        Text(text = stringResource(R.string.countdown))
      },
    )
    NavigationBarItem(
      selected = currentScreen is ScreenTypeStopwatch,
      onClick = {
        if (currentScreen is ScreenTypeStopwatch) {
          return@NavigationBarItem
        }
        nav.navigate("stopwatch")
      },
      icon = {
        Icon(Icons.Default.Timer, contentDescription = stringResource(R.string.stopwatch))
      },
      modifier = Modifier,
      label = {
        Text(text = stringResource(R.string.stopwatch))
      },
    )
  }
}