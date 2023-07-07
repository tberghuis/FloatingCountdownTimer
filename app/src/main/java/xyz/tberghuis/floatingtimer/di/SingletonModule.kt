package xyz.tberghuis.floatingtimer.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import xyz.tberghuis.floatingtimer.data.PreferencesRepository
import xyz.tberghuis.floatingtimer.data.dataStore

@InstallIn(SingletonComponent::class)
@Module
object SingletonModule {

  @Provides
  @Singleton
  fun providePreferencesRepository(@ApplicationContext appContext: Context): PreferencesRepository {
    return PreferencesRepository(appContext.dataStore)
  }
}