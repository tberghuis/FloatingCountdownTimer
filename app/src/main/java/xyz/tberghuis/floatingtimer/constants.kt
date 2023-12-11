package xyz.tberghuis.floatingtimer

import android.content.res.Resources
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

const val TRASH_SIZE_DP = 80


//const val TIMER_SIZE_DP = 60
val TIMER_SIZE_NO_SCALE = 60.dp
//val TIMER_SIZE_PX = (TIMER_SIZE_DP * Resources.getSystem().displayMetrics.density).toInt()
val TIMER_FONT_SIZE_NO_SCALE = 16.sp

const val INTENT_COMMAND = "xyz.tberghuis.floatingtimer.COMMAND"

//val PROGRESS_ARC_WIDTH = 8.dp
val ARC_WIDTH_NO_SCALE = 8.dp

const val FOREGROUND_SERVICE_NOTIFICATION_ID = 1
const val NOTIFICATION_CHANNEL = "xyz.tberghuis.floatingtimer.ONGOING_OVERLAY"
const val NOTIFICATION_CHANNEL_DISPLAY = "Ongoing Timer"

const val REQUEST_CODE_ACTION_MANAGE_OVERLAY_PERMISSION = 1

const val REQUEST_CODE_EXIT = 2
const val REQUEST_CODE_RESET = 3

const val INTENT_COMMAND_EXIT = "command_exit"
const val INTENT_COMMAND_RESET = "command_reset"
