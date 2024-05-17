package xyz.tberghuis.floatingtimer.composables

import androidx.activity.ComponentActivity
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.lifecycle.viewmodel.compose.viewModel
import xyz.tberghuis.floatingtimer.LocalNavController
import xyz.tberghuis.floatingtimer.R
import xyz.tberghuis.floatingtimer.viewmodels.SharedVm

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun FtTopAppBar() {
  val sharedVm: SharedVm = viewModel(LocalContext.current as ComponentActivity)
  val navController = LocalNavController.current
  var showMenu by remember { mutableStateOf(false) }
  TopAppBar(
    title = { Text(stringResource(R.string.app_name)) },
    modifier = Modifier,
    actions = {
      IconButton(
        onClick = {
          showMenu = true
        },
        modifier = Modifier
          .semantics { testTagsAsResourceId = true }
          .testTag("overflow_menu"),
      ) {
        Icon(Icons.Filled.MoreVert, stringResource(R.string.settings))
      }
      DropdownMenu(
        expanded = showMenu,
        onDismissRequest = { showMenu = false },
      ) {
        DropdownMenuItem(
          text = { Text(stringResource(R.string.default_color)) },
          onClick = {
            navController.navigate("change_color")
          },
          modifier = Modifier
            .semantics { testTagsAsResourceId = true }
            .testTag("change_color_menu_item"),
        )
        DropdownMenuItem(
          text = { Text(stringResource(R.string.change_size)) },
          onClick = { navController.navigate("change_size") },
          modifier = Modifier
            .semantics { testTagsAsResourceId = true }
            .testTag("change_size_menu_item"),
        )
        DropdownMenuItem(
          text = { Text(stringResource(R.string.cancel_all_timers)) },
          onClick = {
            showMenu = false
            sharedVm.cancelAllTimers()
          },
        )
        DropdownMenuItem(
          text = { Text("Premium") },
          onClick = {
//            showMenu = false
            navController.navigate("premium")
          },
        )
      }
    },
  )
}