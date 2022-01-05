package com.example.weatherlogger.di

import com.example.weatherlogger.repository.repositoryinterface.LocationRepositoryInterface
import com.example.weatherlogger.repository.repositoryinterface.WeatherDataRepositoryInterface
import com.example.weatherlogger.repository.impl.LocationRepository
import com.example.weatherlogger.repository.impl.WeatherDataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class RepositoryInterfaceModule {

    @Binds
    abstract fun bindLocationRepository(
        locationRepository: LocationRepository
    ): LocationRepositoryInterface

    @Binds
    abstract fun bindWeatherDataRepository(
        weatherDataRepository: WeatherDataRepository
    ): WeatherDataRepositoryInterface
}
