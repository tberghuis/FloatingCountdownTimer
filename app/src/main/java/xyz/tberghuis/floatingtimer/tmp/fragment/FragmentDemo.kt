package xyz.tberghuis.floatingtimer.tmp.fragment

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun FragmentDemo() {
  val context = LocalContext.current
  Column {
    Button(onClick = {
      showOverlay(context)
    }) {
      Text("show overlay")
    }
  }
}

fun showOverlay(context: Context){
  val intent = Intent(context.applicationContext, FragmentService::class.java)
  context.startForegroundService(intent)
}

