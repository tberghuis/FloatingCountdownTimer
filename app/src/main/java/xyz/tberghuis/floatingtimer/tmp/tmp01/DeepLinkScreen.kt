package xyz.tberghuis.floatingtimer.tmp.tmp01

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun DeepLinkScreen(
  vm: DeepLinkScreenVm = viewModel()
) {
  // todo topbar
  Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
    Column(
      modifier = Modifier.padding(innerPadding),
      verticalArrangement = Arrangement.Top,
      horizontalAlignment = Alignment.Start,
    ) {
      Text("hello deeplink")
      Text("link: ${vm.uiLink}")
      Text("timer type: ${vm.uiTimerType}")
      Text("auto start: ${vm.uiStart}")
      Text("result: ${vm.uiResult}")
      Button(onClick = {
        // todo
      }) {
        // todo strings.xml
        Text("close")
      }
    }
  }
}