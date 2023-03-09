package xyz.tberghuis.floatingtimer.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import xyz.tberghuis.floatingtimer.countdown.CountdownAlarmRepository

val Context.dataStore by preferencesDataStore(
  name = "user_preferences",
)

@InstallIn(SingletonComponent::class)
@Module
object SingletonModule {

  @Provides
  @Singleton
  fun provideDataStore(@ApplicationContext appContext: Context): DataStore<Preferences> {
    return appContext.dataStore
  }

  @Provides
  @Singleton
  fun provideCountdownAlarmRepository(dataStore: DataStore<Preferences>): CountdownAlarmRepository {
    return CountdownAlarmRepository(dataStore)
  }
}