package xyz.tberghuis.floatingtimer.tmp

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.data.dataStore
import xyz.tberghuis.floatingtimer.logd


class FakeVm {

  val vmScope = CoroutineScope(Dispatchers.IO)

  // prevent run twice if configuration changed
  // this is being pedantic
  var promptRunOnce = false

  fun promptNotificationPermission(context: Context, prompt: () -> Unit) {
    logd("promptNotificationPermission")
    if (promptRunOnce) {
      logd("promptRunOnce true")
      return
    }
    logd("promptRunOnce false")
    promptRunOnce = true

    vmScope.launch {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        // check if first run, to determine if to prompt
        val firstRun = context.dataStore.data.map { preferences ->
          preferences[booleanPreferencesKey("first_run")]
        }.first()
        if (firstRun == false) {
          return@launch
        }

        // check if granted permission
        val permission = ContextCompat.checkSelfPermission(
          context, Manifest.permission.POST_NOTIFICATIONS
        )
        if (permission != PackageManager.PERMISSION_GRANTED) {
          prompt()
//          val launcher =
//            (context as MainActivity).registerForActivityResult(ActivityResultContracts.RequestPermission()) { }
//          launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }

        logd("after launch")

        // set first_run to false
        context.dataStore.edit { preferences ->
          preferences[booleanPreferencesKey("first_run")] = false
        }
      }
    }
  }
}

val fakevm = FakeVm()


@SuppressLint("InlinedApi")
@Composable
fun NotificationPermissionDemo() {

  val context = LocalContext.current

  // couldn't figure out how to move into VM
  val launcher =
    rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission(),
      onResult = { })

  fun prompt() {
    launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
  }


  Button(onClick = {
    logd("check permission")
    val permission = ContextCompat.checkSelfPermission(
      context, Manifest.permission.POST_NOTIFICATIONS
    )
    logd("permission $permission")

  }) {
    Text("check permission")
  }


  LaunchedEffect(Unit) {
    fakevm.promptNotificationPermission(context, ::prompt)
  }


}