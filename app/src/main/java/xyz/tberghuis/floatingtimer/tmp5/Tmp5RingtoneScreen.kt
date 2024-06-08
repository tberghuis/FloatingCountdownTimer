package xyz.tberghuis.floatingtimer.tmp5

import android.content.Context
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.map
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
fun Tmp5RingtoneScreenContent(
  padding: PaddingValues,
  vm: Tmp5RingtoneVm = viewModel(),
) {

  val context = LocalContext.current
  val currentRingtoneUri = vm.currentRingtoneUri.collectAsState()

  val currentRingtone = remember {
    derivedStateOf {
      currentRingtoneUri.value?.let { ringtoneFromUri(context, it) }
    }
  }.value

  Column(modifier = Modifier.padding(padding)) {
    Text("hello ringtone screen")
    Row {
      // todo outlined text field
      Text("current: ")
      currentRingtone?.let {
        Text(it.getTitle(context))
      }
    }
  }
}


fun ringtoneFromUri(context: Context, uri: String): Ringtone? {
  return RingtoneManager.getRingtone(context, Uri.parse(uri))
}