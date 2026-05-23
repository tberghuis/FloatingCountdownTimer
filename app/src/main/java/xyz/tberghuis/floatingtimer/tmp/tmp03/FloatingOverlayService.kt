package xyz.tberghuis.floatingtimer.tmp.tmp03

import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.view.Gravity
import android.view.WindowManager
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.DragIndicator
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.lifecycle.setViewTreeViewModelStoreOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Service that displays a floating overlay view using WindowManager and ComposeView.
 */
class FloatingOverlayService : Service() {

  companion object {
    var isRunning = false
      private set
  }

  private lateinit var windowManager: WindowManager
  private var composeView: ComposeView? = null
  private var lifecycleOwner: OverlayLifecycleOwner? = null

  val counter = mutableIntStateOf(0)


  override fun onBind(intent: Intent?): IBinder? = null

  override fun onCreate() {
    super.onCreate()
    isRunning = true
    windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
    showOverlay()

    CoroutineScope(Dispatchers.Main).launch {
      while (true) {
        delay(1000)
        counter.intValue++
      }
    }
  }

  private fun showOverlay() {
    // 1. Initialize our custom LifecycleOwner to bridge Jetpack Compose requirements
    val owner = OverlayLifecycleOwner()
    lifecycleOwner = owner

    // 2. Create the ComposeView
    val view = ComposeView(this).apply {
      setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnDetachedFromWindow)
    }
    composeView = view

    // 3. Set the required ViewTree owners
    view.setViewTreeLifecycleOwner(owner)
    view.setViewTreeViewModelStoreOwner(owner)
    view.setViewTreeSavedStateRegistryOwner(owner)

    // 4. Setup WindowManager Layout Parameters
    val layoutFlag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
    } else {
      @Suppress("DEPRECATION")
      WindowManager.LayoutParams.TYPE_PHONE
    }

    val params = WindowManager.LayoutParams(
      WindowManager.LayoutParams.WRAP_CONTENT,
      WindowManager.LayoutParams.WRAP_CONTENT,
      layoutFlag,
      WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
          WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or
          WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
      PixelFormat.TRANSLUCENT
    ).apply {
      gravity = Gravity.TOP or Gravity.START
      x = 100
      y = 200
    }

    // 5. Define the Composable Content
    view.setContent {
      MaterialTheme {
        FloatingCard(
          onDrag = { dx, dy ->
            params.x += dx.toInt()
            params.y += dy.toInt()
            // Update the overlay position in real-time
            try {
              windowManager.updateViewLayout(view, params)
            } catch (e: IllegalArgumentException) {
              // View might have been removed already
            }
          },
          onClose = {
            stopSelf()
          },
          fos = this
        )
      }
    }

    // 6. Transition lifecycle owner to ON_START & ON_RESUME
    owner.handleLifecycleEvent(Lifecycle.Event.ON_START)
    owner.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)

    // 7. Add view to WindowManager
    windowManager.addView(view, params)
  }

  override fun onDestroy() {
    super.onDestroy()
    isRunning = false
    // Remove the view from the window manager
    composeView?.let { view ->
      try {
        windowManager.removeView(view)
      } catch (e: IllegalArgumentException) {
        // View might not be attached
      }
    }
    // Destroy the lifecycle and clear any view models
    lifecycleOwner?.onDestroy()
    lifecycleOwner = null
    composeView = null
  }
}

@Composable
fun FloatingCard(
  onDrag: (Float, Float) -> Unit,
  onClose: () -> Unit,
  fos: FloatingOverlayService
) {
  // Beautiful Glassmorphic Theme with dark-mode colors
  Card(
    modifier = Modifier
      .width(220.dp)
      .padding(8.dp)
      .clip(RoundedCornerShape(20.dp))
      .border(
        width = 1.dp,
        brush = Brush.linearGradient(
          colors = listOf(
            Color.White.copy(alpha = 0.25f),
            Color.White.copy(alpha = 0.05f)
          )
        ),
        shape = RoundedCornerShape(20.dp)
      )
      .pointerInput(Unit) {
        detectDragGestures { change, dragAmount ->
          change.consume()
          onDrag(dragAmount.x, dragAmount.y)
        }
      },
    colors = CardDefaults.cardColors(
      containerColor = Color(0xFF1E1E2C).copy(alpha = 0.85f)
    ),
    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
  ) {
    Column(
      modifier = Modifier.padding(12.dp)
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          Icon(
            imageVector = Icons.Rounded.DragIndicator,
            contentDescription = "Drag overlay",
            tint = Color(0xFF8E8EA8),
            modifier = Modifier.size(18.dp)
          )
          Text(
            text = "Compose Overlay",
            fontSize = 13.sp,
            color = Color.White
          )
        }
        IconButton(
          onClick = onClose,
          modifier = Modifier.size(24.dp)
        ) {
          Icon(
            imageVector = Icons.Rounded.Close,
            contentDescription = "Close overlay",
            tint = Color.White.copy(alpha = 0.6f),
            modifier = Modifier.size(16.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      Surface(
        color = Color.White.copy(alpha = 0.07f),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(
          modifier = Modifier.padding(8.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Text(
            text = "Status: Active",
            fontSize = 11.sp,
            color = Color(0xFF4CAF50)
          )
          Text(
            text = "SYSTEM_ALERT_WINDOW",
            fontSize = 9.sp,
            color = Color.White.copy(alpha = 0.5f),
            modifier = Modifier.padding(top = 2.dp)
          )
          Text("counter: ${fos.counter.intValue}")
        }
      }
    }
  }
}
