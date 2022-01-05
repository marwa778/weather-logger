package com.example.weatherlogger.repository.repositoryinterface

import com.example.weatherlogger.db.currentweatherdata.CurrentWeatherData
import kotlinx.coroutines.flow.Flow

interface WeatherDataRepositoryInterface {

    fun getAllWeatherData(): Flow<List<CurrentWeatherData>>

    fun getWeatherDataByDate(date: Long): Flow<CurrentWeatherData>

    suspend fun saveCurrentWeatherData(latitude: Double, longitude: Double)
}