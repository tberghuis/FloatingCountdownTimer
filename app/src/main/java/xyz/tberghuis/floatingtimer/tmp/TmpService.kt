package xyz.tberghuis.floatingtimer.tmp

import android.app.Notification
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import xyz.tberghuis.floatingtimer.FOREGROUND_SERVICE_NOTIFICATION_ID
import xyz.tberghuis.floatingtimer.MainActivity
import xyz.tberghuis.floatingtimer.NOTIFICATION_CHANNEL
import xyz.tberghuis.floatingtimer.R
import xyz.tberghuis.floatingtimer.logd

class TmpService : Service() {
  private val job = SupervisorJob()
  val scope = CoroutineScope(Dispatchers.Default + job)

  override fun onCreate() {
    super.onCreate()
  }

  override fun onBind(intent: Intent?): IBinder? {
    return null
  }

  override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
    logd("onStartCommand")

    return START_NOT_STICKY
  }

  fun startForeground() {
    startForeground(FOREGROUND_SERVICE_NOTIFICATION_ID, buildNotification())
  }

  private fun buildNotification(): Notification {
    val pendingIntent: PendingIntent =
      Intent(this, MainActivity::class.java).let { notificationIntent ->
        PendingIntent.getActivity(this, 0, notificationIntent, FLAG_IMMUTABLE)
      }

    val notification: Notification =
      NotificationCompat.Builder(this, NOTIFICATION_CHANNEL).setContentTitle("Floating Timer")
        .setSmallIcon(R.drawable.ic_alarm).setContentIntent(pendingIntent).build()
    return notification
  }

  override fun onDestroy() {
    job.cancel()
    super.onDestroy()
  }
}