package xyz.tberghuis.floatingtimer.tmp

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import xyz.tberghuis.floatingtimer.logd

@Composable
fun NotificationPermissionDemo() {
  Button(onClick = {
    logd("check permission")
  }) {
    Text("check permission")
  }

}