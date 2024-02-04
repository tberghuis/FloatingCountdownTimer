package xyz.tberghuis.floatingtimer.tmp3

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
      selected = currentScreen is TmpScreenTypeHome,
      onClick = {},
      icon = {},
      modifier = Modifier,
      label = {
        Text(text = "Home")
      },
    )
    NavigationBarItem(
      selected = currentScreen is TmpScreenTypeSaved,
      onClick = {},
      icon = {},
      modifier = Modifier,
      label = {
        Text(text = "Saved")
      },
    )
    NavigationBarItem(
      selected = currentScreen is TmpScreenTypeSettings,
      onClick = {},
      icon = {},
      modifier = Modifier,
      label = {
        Text(text = "Settings")
      },
    )
  }
}