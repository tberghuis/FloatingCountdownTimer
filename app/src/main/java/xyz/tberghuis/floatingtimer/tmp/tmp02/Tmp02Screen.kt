package xyz.tberghuis.floatingtimer.tmp.tmp02

import android.content.Intent
import android.net.Uri
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
import androidx.core.net.toUri

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

    Button(onClick = {
      val browserIntent = Intent(
        Intent.ACTION_VIEW,
        "https://html-preview.github.io/?url=https://gist.githubusercontent.com/tberghuis/624bf803d03346754a29eba0902bbe78/raw/fd541f5794de02bdc9b38ee24b1e524929cbe0cc/test-foreground.html".toUri()
      )
      context.startActivity(browserIntent)
    }) {
      Text("open web links")
    }
  }
}