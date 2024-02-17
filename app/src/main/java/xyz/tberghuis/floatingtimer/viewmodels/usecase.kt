package xyz.tberghuis.floatingtimer.viewmodels

import android.app.Application
import kotlinx.coroutines.flow.first
import xyz.tberghuis.floatingtimer.MainApplication
import xyz.tberghuis.floatingtimer.providePreferencesRepository

suspend fun shouldShowPremiumDialogMultipleTimers(application: Application): Boolean {
  val boundFloatingService = (application as MainApplication).boundFloatingService
  val premiumPurchased =
    application.providePreferencesRepository().haloColourPurchasedFlow.first()
  val floatingService = boundFloatingService.provideFloatingService()
  val numBubbles = floatingService.overlayController.getNumberOfBubbles()
  return !premiumPurchased && numBubbles == 2
}