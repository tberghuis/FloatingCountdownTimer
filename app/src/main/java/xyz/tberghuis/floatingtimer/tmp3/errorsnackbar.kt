package xyz.tberghuis.floatingtimer.tmp3

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import xyz.tberghuis.floatingtimer.logd

@Composable
fun ErrorSnackbarDemo() {
  Scaffold(
    modifier = Modifier,
    snackbarHost = {},
  ) { padding ->
    Column(modifier = Modifier.padding(padding)) {
      Button(onClick = {
        logd("ErrorSnackbarDemo")
      }) {
        Text("error")
      }
    }
  }
}