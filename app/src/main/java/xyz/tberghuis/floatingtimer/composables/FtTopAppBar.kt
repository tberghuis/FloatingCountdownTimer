package xyz.tberghuis.floatingtimer.composables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import xyz.tberghuis.floatingtimer.LocalNavController
import xyz.tberghuis.floatingtimer.R

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun FtTopAppBar() {
  val navController = LocalNavController.current
  TopAppBar(
    title = { Text(stringResource(R.string.app_name)) },
    modifier = Modifier,
    actions = {
      IconButton(
        onClick = {
          navController.navigate("settings")
        },
        modifier = Modifier
          .semantics { testTagsAsResourceId = true }
          .testTag("settings"),
      ) {
        Icon(Icons.Filled.Settings, stringResource(R.string.settings))
      }
    },
  )
}