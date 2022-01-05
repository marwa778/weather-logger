package com.example.weatherlogger.di

import android.content.Context
import androidx.room.Room
import com.example.weatherlogger.BuildConfig
import com.example.weatherlogger.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun provideCurrentWeatherDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            BuildConfig.CURRENT_WEATHER_DATABASE_NAME
        ).build()

    @Singleton
    @Provides
    fun provideCurrentWeatherDao(appDatabase: AppDatabase) =
        appDatabase.currentWeatherDataDao()
}