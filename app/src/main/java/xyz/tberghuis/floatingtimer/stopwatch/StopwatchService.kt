package xyz.tberghuis.floatingtimer.stopwatch

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import xyz.tberghuis.floatingtimer.R
import xyz.tberghuis.floatingtimer.logd

// doitwrong until hilt/dagger
//var stopwatchServiceHolder: StopwatchService? = null
lateinit var stopwatchServiceHolder: StopwatchService

class StopwatchService : Service() {

  //var stopwatchOverlayComponent: StopwatchOverlayComponent? = null

  val stopwatchOverlayComponent: StopwatchOverlayComponent by lazy {
    StopwatchOverlayComponent(this)
  }


  override fun onBind(intent: Intent?): IBinder? {
    logd("onbind")
    return null
  }

  override fun onStartCommand(intentOrNull: Intent?, flags: Int, startId: Int): Int {

    logd("onstartcommand")
    // doitwrong
    stopwatchServiceHolder = this


    // todo when command

    // INTENT_COMMAND_CREATE_TIMER
    stopwatchOverlayComponent.showOverlay()


    createNotificationChannel()
    val notification: Notification = buildNotification()
    startForeground(2, notification)
    return START_NOT_STICKY
  }


  private fun buildNotification(): Notification {
    return NotificationCompat.Builder(this, "stopwatch_channel")
      .setContentTitle("Floating Stopwatch")
      .setSmallIcon(R.drawable.ic_alarm)
      // this does nothing on > gingerbread
//      .setContentIntent(pendingIntent)
      .build()
  }


  // todo refactor
  private fun createNotificationChannel() {
    val mChannel = NotificationChannel(
      "stopwatch_channel",
      "stopwatch channel",
      NotificationManager.IMPORTANCE_DEFAULT
    )
    mChannel.description = "stopwatch channel description"
    val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(mChannel)
  }

}

