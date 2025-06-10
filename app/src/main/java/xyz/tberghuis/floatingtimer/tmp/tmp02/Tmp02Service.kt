package xyz.tberghuis.floatingtimer.tmp.tmp02

import android.app.Notification
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryController
import androidx.savedstate.SavedStateRegistryOwner
import xyz.tberghuis.floatingtimer.FOREGROUND_SERVICE_NOTIFICATION_ID
import xyz.tberghuis.floatingtimer.NOTIFICATION_CHANNEL
import xyz.tberghuis.floatingtimer.R

class Tmp02Service : LifecycleService(), SavedStateRegistryOwner {

  private val savedStateRegistryController = SavedStateRegistryController.create(this)
  override val savedStateRegistry: SavedStateRegistry
    get() = savedStateRegistryController.savedStateRegistry

  lateinit var overlayController: Tmp02OverlayController

  override fun onCreate() {
    super.onCreate()

    savedStateRegistryController.performAttach()
    savedStateRegistryController.performRestore(null)

    overlayController = Tmp02OverlayController(this)

    startInForeground()
  }

  override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
    super.onStartCommand(intent, flags, startId)


    overlayController.testOverlay()


    return START_NOT_STICKY
  }

  private fun startInForeground() {
    val notification = buildNotification()
    if (Build.VERSION.SDK_INT >= 34) {
      startForeground(
        FOREGROUND_SERVICE_NOTIFICATION_ID,
        notification,
        ServiceInfo.FOREGROUND_SERVICE_TYPE_SPECIAL_USE,
      )
    } else {
      startForeground(
        FOREGROUND_SERVICE_NOTIFICATION_ID,
        notification,
      )
    }
  }

  private fun buildNotification(): Notification {
    val notification: Notification =
      NotificationCompat.Builder(this, NOTIFICATION_CHANNEL)
        .setContentTitle(application.resources.getString(R.string.app_name))
        .setSmallIcon(R.drawable.ic_alarm)
        .setForegroundServiceBehavior(NotificationCompat.FOREGROUND_SERVICE_IMMEDIATE)
        .build()
    return notification
  }

}

