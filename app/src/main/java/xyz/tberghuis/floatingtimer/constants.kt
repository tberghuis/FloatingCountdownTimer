package xyz.tberghuis.floatingtimer

import androidx.compose.ui.unit.dp

const val TRASH_SIZE_DP = 80
const val TIMER_SIZE_DP = 60

const val INTENT_COMMAND = "xyz.tberghuis.floatingtimer.COMMAND"
const val INTENT_COMMAND_COUNTDOWN_CREATE = "countdown_create"
const val INTENT_COMMAND_COUNTDOWN_COMPLETE = "countdown_complete"
const val INTENT_COMMAND_COUNTDOWN_EXIT = "countdown_exit"
const val INTENT_COMMAND_COUNTDOWN_RESET = "countdown_reset"
const val EXTRA_COUNTDOWN_DURATION = "countdown_duration"

const val INTENT_COMMAND_STOPWATCH_CREATE = "stopwatch_create"
const val INTENT_COMMAND_STOPWATCH_RESET = "stopwatch_reset"
const val INTENT_COMMAND_STOPWATCH_EXIT = "stopwatch_exit"

val PROGRESS_ARC_WIDTH = 8.dp

const val FOREGROUND_SERVICE_NOTIFICATION_ID = 1

const val CHANNEL_DEFAULT_ID = "default_channel"
const val CHANNEL_DEFAULT_NAME = "default channel"
const val CHANNEL_DEFAULT_DESCRIPTION = "default channel description"

const val SERVICE_STOPWATCH_NOTIFICATION_ID = 2
const val CHANNEL_STOPWATCH_ID = "stopwatch_channel"
const val CHANNEL_STOPWATCH_NAME = "stopwatch"
const val CHANNEL_STOPWATCH_DESCRIPTION = "stopwatch channel"



const val REQUEST_CODE_PENDING_ALARM = 0
const val REQUEST_CODE_ACTION_MANAGE_OVERLAY_PERMISSION = 1
const val REQUEST_CODE_EXIT_COUNTDOWN = 2
const val REQUEST_CODE_RESET_COUNTDOWN = 3

const val REQUEST_CODE_EXIT_STOPWATCH_SERVICE = 4
const val REQUEST_CODE_RESET_STOPWATCH_SERVICE = 5