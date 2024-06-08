package xyz.tberghuis.floatingtimer.tmp5

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import xyz.tberghuis.floatingtimer.LocalNavController


@Composable
fun TmpNavHost() {
  val navController = rememberNavController()
  CompositionLocalProvider(LocalNavController provides navController) {
    Tmp5RingtoneScreen()
  }
}


@Composable
fun Tmp5RingtoneScreen() {
  Scaffold(
    modifier = Modifier,
    topBar = { TmpRingtoneTopBar() },
    snackbarHost = {},
  ) { padding ->
    Tmp5RingtoneScreenContent(padding)
  }
}

@Composable
fun Tmp5RingtoneScreenContent(padding: PaddingValues) {
  Column(modifier = Modifier.padding(padding)) {
    Text("hello ringtone screen")
  }
}