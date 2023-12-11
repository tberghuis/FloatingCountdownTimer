package xyz.tberghuis.floatingtimer.tmp2

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import xyz.tberghuis.floatingtimer.LocalNavController
import xyz.tberghuis.floatingtimer.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TmpHome() {
  val navController = LocalNavController.current
  var showMenu by remember { mutableStateOf(false) }

  Scaffold(
    topBar = {
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
              text = { Text("Change color") },
              onClick = {},
            )
            DropdownMenuItem(
              text = { Text("Change size") },
              onClick = {navController.navigate("change_size")},
            )
          }
        },
      )
    },
  ) { padding ->
    Column(Modifier.padding(padding)) {
      Text("tmp home screen")
    }
  }
}