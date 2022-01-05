package com.example.weatherlogger.network.services

import com.example.weatherlogger.network.responses.CurrentWeatherDataResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrentWeatherDataService {
    @GET("/data/2.5/weather")
    suspend fun getCurrentWeatherDataByCoordinates(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String
    ) : CurrentWeatherDataResponse
}