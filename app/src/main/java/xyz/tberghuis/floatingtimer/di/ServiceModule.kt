package xyz.tberghuis.floatingtimer.di

import android.app.Service
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.scopes.ServiceScoped
import xyz.tberghuis.floatingtimer.OverlayComponent
import xyz.tberghuis.floatingtimer.stopwatch.StopwatchOverlayComponent

@Module
@InstallIn(ServiceComponent::class)
object ServiceModule {

  // can i have 2 different Service in the same module???
//  @Provides
//  @ServiceScoped
//  fun provideStopwatchOverlayComponent(service: Service): StopwatchOverlayComponent {
//    return StopwatchOverlayComponent(service as Context, stopwatchOverlayState)
//  }
}