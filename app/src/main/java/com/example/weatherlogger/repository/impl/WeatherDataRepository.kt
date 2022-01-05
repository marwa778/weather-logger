package com.example.weatherlogger.repository.impl

import com.example.weatherlogger.BuildConfig
import com.example.weatherlogger.db.currentweatherdata.CurrentWeatherData
import com.example.weatherlogger.db.currentweatherdata.CurrentWeatherDataDao
import com.example.weatherlogger.network.responses.CurrentWeatherDataResponse
import com.example.weatherlogger.network.services.CurrentWeatherDataService
import com.example.weatherlogger.repository.repositoryinterface.WeatherDataRepositoryInterface
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherDataRepository @Inject constructor (
    private val currentWeatherDataService: CurrentWeatherDataService,
    private val currentWeatherDataDao: CurrentWeatherDataDao) : WeatherDataRepositoryInterface {

    override fun getAllWeatherData(): Flow<List<CurrentWeatherData>> = currentWeatherDataDao.getAllWeatherData()

    override fun getWeatherDataByDate(date: Long): Flow<CurrentWeatherData> = currentWeatherDataDao.getWeatherDataByDate(date)

    override suspend fun saveCurrentWeatherData(latitude: Double, longitude: Double) {
        val currentWeatherDataResponse = currentWeatherDataService
            .getCurrentWeatherDataByCoordinates(latitude,longitude, BuildConfig.CURRENT_WEATHER_API_KEY)
        val currentWeatherData = mapCurrentWeatherDataResponseToCurrentWeatherData(currentWeatherDataResponse)
        currentWeatherDataDao.insertOrReplaceWeatherData(currentWeatherData)
    }
    
    private fun mapCurrentWeatherDataResponseToCurrentWeatherData(
        currentWeatherDataResponse: CurrentWeatherDataResponse) =
        CurrentWeatherData(
             date = currentWeatherDataResponse.dt,
             name = currentWeatherDataResponse.name,
             lon = currentWeatherDataResponse.coord.lon,
             lat = currentWeatherDataResponse.coord.lat,
             main = currentWeatherDataResponse.weather.first().main,
             description = currentWeatherDataResponse.weather.first().description,
             temperature = currentWeatherDataResponse.main.temp,
             humidity = currentWeatherDataResponse.main.humidity,
             speed = currentWeatherDataResponse.wind.speed,
             degree = currentWeatherDataResponse.wind.deg,
             sunrise = currentWeatherDataResponse.sys.sunrise,
             sunset = currentWeatherDataResponse.sys.sunset,
             country = currentWeatherDataResponse.sys.country
        )
}