package xyz.tberghuis.floatingtimer.tmp4

import xyz.tberghuis.floatingtimer.logd
import android.app.Notification
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryController
import androidx.savedstate.SavedStateRegistryOwner
import xyz.tberghuis.floatingtimer.FOREGROUND_SERVICE_NOTIFICATION_ID
import xyz.tberghuis.floatingtimer.INTENT_COMMAND
import xyz.tberghuis.floatingtimer.INTENT_COMMAND_EXIT
import xyz.tberghuis.floatingtimer.INTENT_COMMAND_RESET
import xyz.tberghuis.floatingtimer.MainActivity
import xyz.tberghuis.floatingtimer.NOTIFICATION_CHANNEL
import xyz.tberghuis.floatingtimer.R
import xyz.tberghuis.floatingtimer.REQUEST_CODE_EXIT
import xyz.tberghuis.floatingtimer.REQUEST_CODE_RESET
import xyz.tberghuis.floatingtimer.tmp6.ServiceBinder
import xyz.tberghuis.floatingtimer.tmp6.TmpOverlayController

class TmpService : LifecycleService(), SavedStateRegistryOwner {
  lateinit var overlayController: TmpOverlayController


  private val savedStateRegistryController = SavedStateRegistryController.create(this)
  override val savedStateRegistry: SavedStateRegistry
    get() = savedStateRegistryController.savedStateRegistry

  private val binder = LocalBinder()

  inner class LocalBinder : Binder(), ServiceBinder<TmpService> {
    override fun getService(): TmpService = this@TmpService
  }


  init {
    logd("TmpService init")
  }

  override fun onBind(intent: Intent): IBinder {
    super.onBind(intent)
    logd("onbind")
    return binder
  }

  override fun onCreate() {
    super.onCreate()
    savedStateRegistryController.performAttach()
    savedStateRegistryController.performRestore(null)
    overlayController = TmpOverlayController(this)
    startInForeground()
  }

  override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
    super.onStartCommand(intent, flags, startId)
    logd("TmpService onStartCommand")
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
    val pendingIntent: PendingIntent =
      Intent(this, MainActivity::class.java).let { notificationIntent ->
        PendingIntent.getActivity(this, 0, notificationIntent, FLAG_IMMUTABLE)
      }

    val exitIntent = Intent(applicationContext, TmpService::class.java)
    exitIntent.putExtra(INTENT_COMMAND, INTENT_COMMAND_EXIT)
    val exitPendingIntent = PendingIntent.getService(
      applicationContext,
      REQUEST_CODE_EXIT,
      exitIntent,
      FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE
    )

    val resetIntent = Intent(applicationContext, TmpService::class.java)
    resetIntent.putExtra(INTENT_COMMAND, INTENT_COMMAND_RESET)
    val resetPendingIntent = PendingIntent.getService(
      applicationContext,
      REQUEST_CODE_RESET,
      resetIntent,
      FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE
    )

    val notification: Notification =
      NotificationCompat.Builder(this, NOTIFICATION_CHANNEL)
        .setContentTitle(application.resources.getString(R.string.app_name))
        .setSmallIcon(R.drawable.ic_alarm).setContentIntent(pendingIntent).addAction(
          0, application.resources.getString(R.string.reset), resetPendingIntent
        ).addAction(
          0, application.resources.getString(R.string.exit), exitPendingIntent
        )
        .setForegroundServiceBehavior(NotificationCompat.FOREGROUND_SERVICE_IMMEDIATE)
        .build()
    return notification
  }


}

