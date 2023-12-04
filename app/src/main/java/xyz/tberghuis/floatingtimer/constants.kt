package xyz.tberghuis.floatingtimer

import android.content.res.Resources
import androidx.compose.ui.unit.dp

const val TRASH_SIZE_DP = 80
const val TIMER_SIZE_DP = 60
val TIMER_SIZE_PX = (TIMER_SIZE_DP * Resources.getSystem().displayMetrics.density).toInt()

const val INTENT_COMMAND = "xyz.tberghuis.floatingtimer.COMMAND"

// todo delete
const val INTENT_COMMAND_COUNTDOWN_CREATE = "countdown_create"

const val INTENT_COMMAND_COUNTDOWN_COMPLETE = "countdown_complete"
const val EXTRA_COUNTDOWN_DURATION = "countdown_duration"

// todo delete
const val INTENT_COMMAND_STOPWATCH_CREATE = "stopwatch_create"

val PROGRESS_ARC_WIDTH = 8.dp

const val FOREGROUND_SERVICE_NOTIFICATION_ID = 1
const val NOTIFICATION_CHANNEL = "xyz.tberghuis.floatingtimer.ONGOING_OVERLAY"
const val NOTIFICATION_CHANNEL_DISPLAY = "Ongoing Timer"

const val REQUEST_CODE_PENDING_ALARM = 0
const val REQUEST_CODE_ACTION_MANAGE_OVERLAY_PERMISSION = 1

const val REQUEST_CODE_EXIT = 2
const val REQUEST_CODE_RESET = 3

const val INTENT_COMMAND_EXIT = "command_exit"
const val INTENT_COMMAND_RESET = "command_reset"
