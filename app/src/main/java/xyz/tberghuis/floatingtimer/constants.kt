package xyz.tberghuis.floatingtimer

import androidx.compose.ui.unit.dp

const val TRASH_SIZE_DP = 80
const val TIMER_SIZE_DP = 60

const val INTENT_COMMAND = "xyz.tberghuis.floatingtimer.COMMAND"
const val INTENT_COMMAND_SHOW_OVERLAY = "command_show_overlay"
const val INTENT_COMMAND_CREATE_TIMER = "command_create_timer"
const val INTENT_COMMAND_COUNTDOWN_COMPLETE = "command_countdown_complete"
const val INTENT_COMMAND_EXIT = "command_exit"
const val EXTRA_TIMER_DURATION = "extra_timer_duration"

val PROGRESS_ARC_WIDTH = 8.dp

const val FOREGROUND_SERVICE_NOTIFICATION_ID = 1

const val CHANNEL_DEFAULT_ID = "default_channel"
const val CHANNEL_DEFAULT_NAME = "default channel"
const val CHANNEL_DEFAULT_DESCRIPTION = "default channel description"

const val REQUEST_CODE_PENDING_ALARM = 0
const val REQUEST_CODE_ACTION_MANAGE_OVERLAY_PERMISSION = 1
const val REQUEST_CODE_EXIT_SERVICE = 2