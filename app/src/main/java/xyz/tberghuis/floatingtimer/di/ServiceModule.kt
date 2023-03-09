package xyz.tberghuis.floatingtimer.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.scopes.ServiceScoped
import xyz.tberghuis.floatingtimer.countdown.CountdownAlarmRepository
import xyz.tberghuis.floatingtimer.countdown.CountdownState

@Module
@InstallIn(ServiceComponent::class)
object ServiceModule {

  // can i have 2 different Service in the same module???
//  @Provides
//  @ServiceScoped
//  fun provideStopwatchOverlayComponent(service: Service): StopwatchOverlayComponent {
//    return StopwatchOverlayComponent(service as Context, stopwatchOverlayState)
//  }


  @Provides
  @ServiceScoped
  fun provideCountdownState(alarmRepository: CountdownAlarmRepository): CountdownState {
    return CountdownState(alarmRepository)
  }

}