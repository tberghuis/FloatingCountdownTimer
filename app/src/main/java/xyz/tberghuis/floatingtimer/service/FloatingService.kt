package xyz.tberghuis.floatingtimer.service

import android.app.Notification
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Intent
import android.content.pm.ServiceInfo
import android.content.res.Configuration
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryController
import androidx.savedstate.SavedStateRegistryOwner
import com.torrydo.screenez.ScreenEz
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import xyz.tberghuis.floatingtimer.FOREGROUND_SERVICE_NOTIFICATION_ID
import xyz.tberghuis.floatingtimer.INTENT_COMMAND
import xyz.tberghuis.floatingtimer.INTENT_COMMAND_EXIT
import xyz.tberghuis.floatingtimer.INTENT_COMMAND_RESET
import xyz.tberghuis.floatingtimer.MainActivity
import xyz.tberghuis.floatingtimer.NOTIFICATION_CHANNEL
import xyz.tberghuis.floatingtimer.R
import xyz.tberghuis.floatingtimer.REQUEST_CODE_EXIT
import xyz.tberghuis.floatingtimer.REQUEST_CODE_RESET
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.tmp4.TmpOverlayController

// https://stackoverflow.com/questions/76503237/how-to-use-jetpack-compose-in-service
class FloatingService : LifecycleService(), SavedStateRegistryOwner {
  private val job = SupervisorJob()

  // Main.immediate to prevent ANRs user input... ???
  val scope = CoroutineScope(Dispatchers.Default + job)

  lateinit var alarmController: FtAlarmController
  lateinit var overlayController: TmpOverlayController

  private val savedStateRegistryController = SavedStateRegistryController.create(this)

  override val savedStateRegistry: SavedStateRegistry
    get() = savedStateRegistryController.savedStateRegistry

  private val binder = LocalBinder()

  inner class LocalBinder : Binder() {
    fun getService(): FloatingService = this@FloatingService
  }

  override fun onBind(intent: Intent): IBinder {
    super.onBind(intent)
    logd("onbind")
    return binder
  }

  override fun onCreate() {
    super.onCreate()
    ScreenEz.with(this.applicationContext)

    savedStateRegistryController.performAttach()
    savedStateRegistryController.performRestore(null)

    alarmController = FtAlarmController(this)
    overlayController = TmpOverlayController(this)
    startInForeground()
  }

  override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
    super.onStartCommand(intent, flags, startId)
    logd("FloatingService onStartCommand")

    intent?.let {
      when (intent.getStringExtra(INTENT_COMMAND)) {
        INTENT_COMMAND_EXIT -> {
          overlayController.exitAll()
        }

        INTENT_COMMAND_RESET -> {
          overlayController.resetAll()
        }

        else -> {}
      }
    }
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

    val exitIntent = Intent(applicationContext, FloatingService::class.java)
    exitIntent.putExtra(INTENT_COMMAND, INTENT_COMMAND_EXIT)
    val exitPendingIntent = PendingIntent.getService(
      applicationContext,
      REQUEST_CODE_EXIT,
      exitIntent,
      FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE
    )

    val resetIntent = Intent(applicationContext, FloatingService::class.java)
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

  override fun onDestroy() {
    job.cancel()
    super.onDestroy()
  }

  override fun onConfigurationChanged(newConfig: Configuration) {
    super.onConfigurationChanged(newConfig)
    ScreenEz.refresh()
    overlayController.onConfigurationChanged()
  }
}