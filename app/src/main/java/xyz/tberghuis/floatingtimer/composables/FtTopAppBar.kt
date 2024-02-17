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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import xyz.tberghuis.floatingtimer.LocalNavController
import xyz.tberghuis.floatingtimer.R
import xyz.tberghuis.floatingtimer.tmp4.TmpSharedVm

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FtTopAppBar() {
  val sharedVm: TmpSharedVm = viewModel(LocalContext.current as ComponentActivity)
  val navController = LocalNavController.current
  var showMenu by remember { mutableStateOf(false) }
  TopAppBar(
    title = { Text(stringResource(R.string.app_name)) },
    modifier = Modifier,
    actions = {
      IconButton(onClick = {
        showMenu = true
      }) {
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
        )
        DropdownMenuItem(
          text = { Text(stringResource(R.string.change_size)) },
          onClick = { navController.navigate("change_size") },
        )
        DropdownMenuItem(
          text = { Text(stringResource(R.string.cancel_all_timers)) },
          onClick = {
            showMenu = false
            sharedVm.cancelAllTimers()
          },
        )
      }
    },
  )
}