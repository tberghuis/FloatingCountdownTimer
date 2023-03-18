package xyz.tberghuis.floatingtimer.tmp

import android.R
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.FLAG_INSISTENT
import xyz.tberghuis.floatingtimer.INTENT_COMMAND
import xyz.tberghuis.floatingtimer.INTENT_COMMAND_COUNTDOWN_COMPLETE
import xyz.tberghuis.floatingtimer.logd

class TmpAlarmReceiver : BroadcastReceiver() {
  override fun onReceive(context: Context, intent: Intent?) {
    logd("onReceive start")
    logd("context $context")

  }

  // no need for this
  @SuppressLint("NotificationPermission")
  private fun sendNotification(context: Context, intent: Intent?) {
    createNotificationChannel(context)

    val pendingIntent =
      PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_IMMUTABLE)
    val notificationBuilder: NotificationCompat.Builder =
      NotificationCompat.Builder(context, "alarm_channel")
        .setSmallIcon(R.drawable.ic_lock_idle_alarm)
        .setContentTitle("alarm title")
        .setContentText("alarm text")
        .setLargeIcon(
          BitmapFactory.decodeResource(
            context.resources,
            R.drawable.ic_notification_overlay
          )
        )
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)
        // let the service be in control of playing alarm sound + vibrate
        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM))
        .setVibrate(longArrayOf(800, 500, 600, 300))

    val notification = notificationBuilder.build()
    notification.flags = notification.flags or FLAG_INSISTENT
//    notification.flags = FLAG_INSISTENT
    logd(
      "flags ${notification.flags}"
    )
    val manager: NotificationManager = context.getSystemService(NotificationManager::class.java)
    // TODO use ALARM_NOTIFICATION_ID
    manager.notify(2, notification)
    logd("onReceive end")
  }

  private fun createNotificationChannel(context: Context) {
    val serviceChannel = NotificationChannel(
      "alarm_channel",
      "Alarm Channel",
      NotificationManager.IMPORTANCE_HIGH
    )
    val manager: NotificationManager = context.getSystemService(NotificationManager::class.java)
    manager.createNotificationChannel(serviceChannel)
  }


}