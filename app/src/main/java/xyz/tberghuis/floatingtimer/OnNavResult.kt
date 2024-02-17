package xyz.tberghuis.floatingtimer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavBackStackEntry

// inspired from
// https://stackoverflow.com/questions/66837132/jetpack-compose-navigate-for-result
@Composable
fun <T> NavBackStackEntry.OnNavResult(savedStateHandleKey: String, onResult: (T) -> Unit) {
  LaunchedEffect(Unit) {
    val result = savedStateHandle.get<T>(savedStateHandleKey)
    result?.let {
      onResult(it)
      // ensure onResult only once (configuration change)
      savedStateHandle.remove<T>(savedStateHandleKey)
    }
  }
}