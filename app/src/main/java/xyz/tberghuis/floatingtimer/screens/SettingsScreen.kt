package xyz.tberghuis.floatingtimer.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen() {

  Scaffold(
    modifier = Modifier,
    topBar = {
      TopAppBar(
        title = { Text("Settings") },
        modifier = Modifier,
      )
    },
    snackbarHost = {},
  ) { padding ->
    Column(
      modifier = Modifier.padding(padding),
    ) {
      Text("settings")
    }
  }


}