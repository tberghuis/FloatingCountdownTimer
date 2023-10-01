package xyz.tberghuis.floatingtimer.tmp2

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.PixelFormat
import android.util.Log
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.WindowManager
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import com.torrydo.screenez.ScreenEz
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import xyz.tberghuis.floatingtimer.LocalHaloColour
import xyz.tberghuis.floatingtimer.TIMER_SIZE_PX
import xyz.tberghuis.floatingtimer.logd
import xyz.tberghuis.floatingtimer.providePreferencesRepository
import xyz.tberghuis.floatingtimer.service.FloatingService
import xyz.tberghuis.floatingtimer.service.IsDraggingOverlay
import xyz.tberghuis.floatingtimer.service.OverlayState
import xyz.tberghuis.floatingtimer.service.countdown.CountdownBubble
import xyz.tberghuis.floatingtimer.service.countdown.TimerStateFinished
import xyz.tberghuis.floatingtimer.service.countdown.TimerStatePaused
import xyz.tberghuis.floatingtimer.service.countdown.TimerStateRunning
import xyz.tberghuis.floatingtimer.service.onClickStopwatchClickTarget
import xyz.tberghuis.floatingtimer.service.stopwatch.StopwatchBubble
import kotlin.math.max
import kotlin.math.min
