package xyz.tberghuis.floatingtimer

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

const val TRASH_SIZE_DP = 80

val TIMER_SIZE_NO_SCALE = 60.dp
val TIMER_FONT_SIZE_NO_SCALE = 16.sp

const val INTENT_COMMAND = "xyz.tberghuis.floatingtimer.COMMAND"

val ARC_WIDTH_NO_SCALE = 8.dp

const val FOREGROUND_SERVICE_NOTIFICATION_ID = 1
const val NOTIFICATION_CHANNEL = "xyz.tberghuis.floatingtimer.ONGOING_OVERLAY"
const val NOTIFICATION_CHANNEL_DISPLAY = "Ongoing Timer"

const val REQUEST_CODE_ACTION_MANAGE_OVERLAY_PERMISSION = 1

const val REQUEST_CODE_EXIT = 2
const val REQUEST_CODE_RESET = 3

const val INTENT_COMMAND_EXIT = "command_exit"
const val INTENT_COMMAND_RESET = "command_reset"

// this is same as MaterialTheme.colorScheme.primary outside of FloatingTimerTheme
val DEFAULT_HALO_COLOR = Color(red = 103, green = 80, blue = 164)

const val DB_FILENAME = "ft.db"