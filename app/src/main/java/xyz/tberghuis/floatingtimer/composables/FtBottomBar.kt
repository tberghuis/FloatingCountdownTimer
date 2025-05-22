package xyz.tberghuis.floatingtimer.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import xyz.tberghuis.floatingtimer.LocalNavController
import xyz.tberghuis.floatingtimer.R
import xyz.tberghuis.floatingtimer.screens.ScreenType
import xyz.tberghuis.floatingtimer.screens.ScreenTypeCountdown
import xyz.tberghuis.floatingtimer.screens.ScreenTypeStopwatch

@Composable
fun FtBottomBar(currentScreen: ScreenType) {
  val nav = LocalNavController.current

  // https://github.com/android/compose-samples/blob/main/Reply/app/src/main/java/com/example/reply/ui/navigation/ReplyNavigationActions.kt
  fun navigateTo(route: String) {
    nav.navigate(route) {
      popUpTo(nav.graph.findStartDestination().id) {
        saveState = true
      }
      launchSingleTop = true
      restoreState = true
    }
  }

  NavigationBar(
//    windowInsets = WindowInsets.navigationBars
  ) {
    Row(
      Modifier.padding(
//        bottom = WindowInsets.safeContent.asPaddingValues()
//          .calculateBottomPadding() - WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
        bottom = 10.dp
      )
    ) {
      NavigationBarItem(
        selected = currentScreen is ScreenTypeCountdown,
        onClick = {
          if (currentScreen is ScreenTypeCountdown) {
            return@NavigationBarItem
          }
          navigateTo("countdown")
        },
        icon = {
          Icon(Icons.Default.Timer, contentDescription = stringResource(R.string.countdown))
        },
        modifier = Modifier
          .semantics { testTagsAsResourceId = true }
          .testTag("NavCountdown"),
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
          navigateTo("stopwatch")
        },
        icon = {
          Icon(Icons.Default.Timer, contentDescription = stringResource(R.string.stopwatch))
        },
        modifier = Modifier
          .semantics { testTagsAsResourceId = true }
          .testTag("NavStopwatch"),
        label = {
          Text(text = stringResource(R.string.stopwatch))
        },
      )
    }
  }
}