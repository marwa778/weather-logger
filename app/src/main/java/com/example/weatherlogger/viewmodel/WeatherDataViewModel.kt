package com.example.weatherlogger.viewmodel

import androidx.lifecycle.ViewModel
import com.example.weatherlogger.db.currentweatherdata.CurrentWeatherData
import com.example.weatherlogger.repository.LocationRepository
import com.example.weatherlogger.repository.WeatherDataRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherDataViewModel @Inject constructor(
    private val weatherDataRepository: WeatherDataRepository,
    private val locationRepository: LocationRepository,
): ViewModel() {

    suspend fun saveCurrentWeatherData() {
        val coordinates = locationRepository.getCurrentLocation()
        weatherDataRepository.saveCurrentWeatherData(coordinates.lat, coordinates.lon)
    }

    fun getAllWeatherData(): Flow<List<CurrentWeatherData>> = weatherDataRepository.getAllWeatherData()

    fun getWeatherDataByDate(date: Long): Flow<CurrentWeatherData> = weatherDataRepository.getWeatherDataByDate(date)
}