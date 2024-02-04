package xyz.tberghuis.floatingtimer.tmp3

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun TmpHomeScreen() {
  // doitwrong, repeat scaffold for each route
  Scaffold(
    modifier = Modifier,
    topBar = {},
    bottomBar = {},
  ) { padding ->
    Column(
      modifier = Modifier.padding(padding),
      verticalArrangement = Arrangement.Top,
      horizontalAlignment = Alignment.Start,
    ) {
      Text("hello home")
    }
  }
}