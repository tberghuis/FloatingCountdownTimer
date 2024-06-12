package xyz.tberghuis.floatingtimer.composables

import androidx.compose.runtime.Composable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import xyz.tberghuis.floatingtimer.LocalNavController
import xyz.tberghuis.floatingtimer.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RingtoneTopBar() {
  val navController = LocalNavController.current
  TopAppBar(
    title = { Text(stringResource(R.string.countdown_ringtone)) },
    navigationIcon = {
      IconButton(onClick = {
        navController.navigateUp()
      }) {
        Icon(Icons.AutoMirrored.Filled.ArrowBack, stringResource(R.string.back))
      }
    },
    modifier = Modifier,
  )
}