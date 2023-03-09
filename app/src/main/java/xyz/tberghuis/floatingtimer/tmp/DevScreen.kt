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
import xyz.tberghuis.floatingtimer.INTENT_COMMAND_SHOW_OVERLAY
import xyz.tberghuis.floatingtimer.countdown.CountdownService
import xyz.tberghuis.floatingtimer.logd

@Composable
fun DevScreen() {
  val context = LocalContext.current
  Column(modifier = Modifier.background(Color.Yellow)) {
    Text("hello dev screen")
    Button(onClick = {
      logd("persistent notification")
      val intent = Intent(context.applicationContext, CountdownService::class.java)
      context.startForegroundService(intent)
    }) {
      Text("persistent notification")
    }
    PermissionDemo()
    StartOverlay()
    BindService()
  }
}

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
    val intent = Intent(context.applicationContext, CountdownService::class.java)
    intent.putExtra(INTENT_COMMAND, INTENT_COMMAND_SHOW_OVERLAY)
    context.startForegroundService(intent)
  }) {
    Text("start overlay")
  }
}

// doitwrong
// deal with changing activity rotation
var foregroundService: CountdownService? = null

@Composable
fun BindService() {
  val context = LocalContext.current
  Button(onClick = {
    logd("bind service")
    val connection = object : ServiceConnection {
      override fun onServiceConnected(className: ComponentName, service: IBinder) {
        // We've bound to LocalService, cast the IBinder and get LocalService instance
        val binder = service as CountdownService.LocalBinder
        foregroundService = binder.getService()
      }
      override fun onServiceDisconnected(arg0: ComponentName) {
        foregroundService = null
      }
    }
    Intent(context, CountdownService::class.java).also { intent ->
      context.bindService(intent, connection, Context.BIND_AUTO_CREATE)
    }
  }) {
    Text("bind service")
  }
}