package xyz.tberghuis.floatingtimer.tmp

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import xyz.tberghuis.floatingtimer.logd


@Composable
fun MaccasScreen() {
  val context = LocalContext.current

  Column {
    Button(onClick = {
      logd("startMaccasService")
      startMaccasService(context)
    }) {
      Text("startMaccasService")
    }
  }
}

fun startMaccasService(context: Context) {
  val intent = Intent(context.applicationContext, MaccasService::class.java)
  context.startForegroundService(intent)
}

