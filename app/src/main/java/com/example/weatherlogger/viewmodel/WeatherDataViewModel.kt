package com.example.weatherlogger.viewmodel

import androidx.lifecycle.ViewModel
import com.example.weatherlogger.db.currentweatherdata.CurrentWeatherData
import com.example.weatherlogger.repository.repositoryinterface.LocationRepositoryInterface
import com.example.weatherlogger.repository.repositoryinterface.WeatherDataRepositoryInterface
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherDataViewModel @Inject constructor(
    private val weatherDataRepository: WeatherDataRepositoryInterface,
    private val locationRepository: LocationRepositoryInterface,
): ViewModel() {

    suspend fun saveCurrentWeatherData() {
        val coordinates = locationRepository.getCurrentLocation()
        weatherDataRepository.saveCurrentWeatherData(coordinates.lat, coordinates.lon)
    }

    fun getAllWeatherData(): Flow<List<CurrentWeatherData>> = weatherDataRepository.getAllWeatherData()

    fun getWeatherDataByDate(date: Long): Flow<CurrentWeatherData> = weatherDataRepository.getWeatherDataByDate(date)
}