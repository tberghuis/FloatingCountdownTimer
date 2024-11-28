package xyz.tberghuis.floatingtimer.viewmodels

import android.app.Application
import kotlinx.coroutines.flow.first
import xyz.tberghuis.floatingtimer.data.preferencesRepository
import xyz.tberghuis.floatingtimer.service.boundFloatingServiceProvider

suspend fun shouldShowPremiumDialogMultipleTimers(application: Application): Boolean {
  val boundFloatingService = application.boundFloatingServiceProvider
  val premiumPurchased =
    application.preferencesRepository.haloColourPurchasedFlow.first()
  val floatingService = boundFloatingService.provideService()
  val numBubbles = floatingService.overlayController.getNumberOfBubbles()
  return !premiumPurchased && numBubbles == 2
}