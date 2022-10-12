package xyz.tberghuis.floatingtimer.di

import android.app.Service
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.scopes.ServiceScoped
import xyz.tberghuis.floatingtimer.OverlayComponent

@Module
@InstallIn(ServiceComponent::class)
object ServiceModule {

  @Provides
  @ServiceScoped
  fun provideOverlayComponent(service: Service): OverlayComponent {
    return OverlayComponent(service as Context) {
      // todo test older api levels than 32
      // stopForeground(STOP_FOREGROUND_REMOVE)
      service.stopSelf()
    }
  }
}