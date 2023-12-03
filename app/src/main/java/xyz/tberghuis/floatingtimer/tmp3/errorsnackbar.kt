package xyz.tberghuis.floatingtimer.tmp3

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.logd

@Composable
fun ErrorSnackbarDemo() {

  val snackbarHostState = remember { SnackbarHostState() }
  val scope = rememberCoroutineScope()

  Scaffold(
    modifier = Modifier,
    snackbarHost = {
      SnackbarHost(snackbarHostState)
    },
  ) { padding ->
    Column(modifier = Modifier.padding(padding)) {
      Button(onClick = {
        logd("ErrorSnackbarDemo")
        scope.launch {
          snackbarHostState.showSnackbar(
            "error message here"
          )
        }

      }) {
        Text("error")
      }
    }
  }
}