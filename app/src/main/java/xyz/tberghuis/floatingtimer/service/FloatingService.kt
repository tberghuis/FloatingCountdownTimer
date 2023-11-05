package xyz.tberghuis.floatingtimer.service

import android.app.Notification
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Intent
import android.content.res.Configuration
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryController
import androidx.savedstate.SavedStateRegistryOwner
import com.torrydo.screenez.ScreenEz
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import xyz.tberghuis.floatingtimer.EXTRA_COUNTDOWN_DURATION
import xyz.tberghuis.floatingtimer.FOREGROUND_SERVICE_NOTIFICATION_ID
import xyz.tberghuis.floatingtimer.INTENT_COMMAND
import xyz.tberghuis.floatingtimer.INTENT_COMMAND_COUNTDOWN_COMPLETE
import xyz.tberghuis.floatingtimer.INTENT_COMMAND_COUNTDOWN_CREATE
import xyz.tberghuis.floatingtimer.INTENT_COMMAND_EXIT
import xyz.tberghuis.floatingtimer.INTENT_COMMAND_RESET
import xyz.tberghuis.floatingtimer.INTENT_COMMAND_STOPWATCH_CREATE
import xyz.tberghuis.floatingtimer.MainActivity
import xyz.tberghuis.floatingtimer.NOTIFICATION_CHANNEL
import xyz.tberghuis.floatingtimer.R
import xyz.tberghuis.floatingtimer.REQUEST_CODE_EXIT
import xyz.tberghuis.floatingtimer.REQUEST_CODE_RESET
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.service.countdown.TimerStateFinished
import xyz.tberghuis.floatingtimer.tmp2.OverlayController

// https://stackoverflow.com/questions/76503237/how-to-use-jetpack-compose-in-service

class XxxFloatingService : LifecycleService(), SavedStateRegistryOwner {
  private val job = SupervisorJob()
  val scope = CoroutineScope(Dispatchers.Default + job)
  val state = ServiceState(scope)

  lateinit var overlayController: XxxOverlayController
  lateinit var alarmController: AlarmController

  private val savedStateRegistryController = SavedStateRegistryController.create(this)

  override val savedStateRegistry: SavedStateRegistry
    get() = savedStateRegistryController.savedStateRegistry

  override fun onCreate() {
    super.onCreate()
    ScreenEz.with(this.applicationContext)

    savedStateRegistryController.performAttach()
    savedStateRegistryController.performRestore(null)

    overlayController = XxxOverlayController(this)
    alarmController = AlarmController(this)
  }

//  override fun onBind(intent: Intent): IBinder? {
//    super.onBind(intent)
//    return null
//  }

  override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
    super.onStartCommand(intent, flags, startId)
    logd("FloatingService onStartCommand")
    postOngoingActivityNotification()

    intent?.let {
      val command = intent.getStringExtra(INTENT_COMMAND)
      when (command) {
        INTENT_COMMAND_COUNTDOWN_CREATE -> {
          val duration = intent.getIntExtra(EXTRA_COUNTDOWN_DURATION, 10)
          state.countdownState.resetTimerState(duration)
          state.countdownState.overlayState.isVisible.value = true
        }

        INTENT_COMMAND_COUNTDOWN_COMPLETE -> {
          logd("onStartCommand INTENT_COMMAND_COUNTDOWN_COMPLETE")
          state.countdownState.timerState.value = TimerStateFinished
        }

        INTENT_COMMAND_STOPWATCH_CREATE -> {
          state.stopwatchState.overlayState.isVisible.value = true
        }

        INTENT_COMMAND_EXIT -> {
          if (state.countdownState.overlayState.isVisible.value == true) {
            overlayController.exitCountdown()
          }
          if (state.stopwatchState.overlayState.isVisible.value == true) {
            overlayController.exitStopwatch()
          }
          return START_NOT_STICKY
        }

        INTENT_COMMAND_RESET -> {
          state.stopwatchState.resetStopwatchState()
          state.countdownState.resetTimerState()
        }
      }
    }
    return START_STICKY
  }

  private fun postOngoingActivityNotification() {
    // see if this reduces ANR's if I call for every onStartCommand
    startForeground(FOREGROUND_SERVICE_NOTIFICATION_ID, buildNotification())
//    if (!isStarted) {
//      isStarted = true
//      startForeground(FOREGROUND_SERVICE_NOTIFICATION_ID, buildNotification())
//    }
  }

  private fun buildNotification(): Notification {
    val pendingIntent: PendingIntent =
      Intent(this, MainActivity::class.java).let { notificationIntent ->
        PendingIntent.getActivity(this, 0, notificationIntent, FLAG_IMMUTABLE)
      }

    val exitIntent = Intent(applicationContext, XxxFloatingService::class.java)
    exitIntent.putExtra(INTENT_COMMAND, INTENT_COMMAND_EXIT)
    val exitPendingIntent = PendingIntent.getService(
      applicationContext,
      REQUEST_CODE_EXIT,
      exitIntent,
      FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE
    )

    val resetIntent = Intent(applicationContext, XxxFloatingService::class.java)
    resetIntent.putExtra(INTENT_COMMAND, INTENT_COMMAND_RESET)
    val resetPendingIntent = PendingIntent.getService(
      applicationContext,
      REQUEST_CODE_RESET,
      resetIntent,
      FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE
    )

    val notification: Notification =
      NotificationCompat.Builder(this, NOTIFICATION_CHANNEL).setContentTitle("Floating Timer")
        .setSmallIcon(R.drawable.ic_alarm).setContentIntent(pendingIntent).addAction(
          0, "Reset", resetPendingIntent
        ).addAction(
          0, "Exit", exitPendingIntent
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
    scope.launch {
      state.configurationChanged.emit(Unit)
    }
  }
}