package xyz.tberghuis.floatingtimer.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.unit.Constraints
import kotlin.math.max

// assumes content and background contains a single composable
// use Modifier.fillMaxSize() in background composable
@Composable
fun SquareBackground(
  modifier: Modifier = Modifier,
  background: @Composable () -> Unit,
  content: @Composable () -> Unit
) {
  SubcomposeLayout(
    modifier
  ) { constraints ->
    val contentMeasurable = subcompose("content", content)[0]
    val contentPlaceable = contentMeasurable.measure(constraints)
//    val sideLength = max(contentPlaceable.width, contentPlaceable.height)
    val sideLength = max(contentPlaceable.measuredWidth, contentPlaceable.measuredHeight)
    layout(sideLength, sideLength) {
      val backgroundMeasurable = subcompose("background") {
        Box(
          modifier = Modifier.size(sideLength.toDp()),
        ) {
          background()
        }
      }.first()
      val backgroundConstraints = Constraints(
        minWidth = sideLength,
        maxWidth = sideLength,
        minHeight = sideLength,
        maxHeight = sideLength
      )
      val backgroundPlaceable = backgroundMeasurable.measure(backgroundConstraints)
      backgroundPlaceable.place(0, 0)
      contentPlaceable.placeRelative(
        (sideLength - contentPlaceable.width) / 2,
        (sideLength - contentPlaceable.height) / 2
      )
    }
  }
}