package xyz.tberghuis.floatingtimer.composables

import androidx.compose.runtime.compositionLocalOf
import xyz.tberghuis.floatingtimer.service.FloatingService
import androidx.compose.ui.graphics.Color
import xyz.tberghuis.floatingtimer.service.TrashController

val LocalFloatingService = compositionLocalOf<FloatingService> {
  error("CompositionLocal LocalFloatingService not present")
}

val LocalHaloColour = compositionLocalOf<Color> {
  error("CompositionLocal LocalHaloColour not present")
}

//val LocalTrashController = compositionLocalOf<TrashController> {
//  error("CompositionLocal LocalTrashController not present")
//}
