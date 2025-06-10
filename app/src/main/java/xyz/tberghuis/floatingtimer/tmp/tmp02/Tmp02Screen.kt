package xyz.tberghuis.floatingtimer.tmp.tmp02

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.tmp.tmp01.ProcessNameService

// reproduce foreground service not allowed exception

@Composable
fun Tmp02Screen() {
  val context = LocalContext.current
  Column(
    modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Center,
  ) {
    Button(onClick = {
      logd("start foreground")
      val intent = Intent(context, Tmp02Service::class.java)
      context.startForegroundService(intent)
    }) {
      Text("start foreground")
    }
  }
}