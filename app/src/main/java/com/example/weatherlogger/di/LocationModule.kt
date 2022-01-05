package com.example.weatherlogger.di

import android.content.Context
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext

@InstallIn(ActivityComponent::class)
@Module
object LocationModule {

    @Provides
    fun provideFusedLocationProviderClient(@ActivityContext context: Context) =
        LocationServices.getFusedLocationProviderClient(context)
}