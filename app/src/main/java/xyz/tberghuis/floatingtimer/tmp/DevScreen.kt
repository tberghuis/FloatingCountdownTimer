package xyz.tberghuis.floatingtimer.tmp

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.net.Uri
import android.os.IBinder
import android.provider.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat.startActivityForResult
import xyz.tberghuis.floatingtimer.INTENT_COMMAND
import xyz.tberghuis.floatingtimer.logd


@Composable
fun PermissionDemo() {
  val context = LocalContext.current
  Button(onClick = {
    logd("draw over click")
    // doitwrong
    val intent = Intent(
      Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
      Uri.parse("package:${context.packageName}")
    )
    try {
      startActivityForResult(context as Activity, intent, 0, null)
    } catch (e: Exception) {
      logd("$e")
    }
  }) {
    Text("open draw over other apps settings")
  }
}

@Composable
fun StartOverlay() {
  val context = LocalContext.current
  Button(onClick = {

  }) {
    Text("start overlay")
  }
}
