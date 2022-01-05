package com.example.weatherlogger.db.currentweatherdata

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_data")
data class CurrentWeatherData (
    @PrimaryKey
    val date: Long = 0L,
    val name: String = "",
    val lon: Double = 0.0,
    val lat: Double = 0.0,
    val main: String = "",
    val description: String = "",
    val temperature: Double = 0.0,
    val humidity: Int = 0,
    val speed: Double = 0.0,
    val degree: Int = 0,
    val sunrise: Long = 0L,
    val sunset: Long = 0L,
    val country: String = ""
)
