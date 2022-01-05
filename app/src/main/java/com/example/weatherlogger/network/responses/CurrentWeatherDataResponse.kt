package com.example.weatherlogger.network.responses

data class CurrentWeatherDataResponse (
    val dt: Long = 0L,
    val name: String = "",
    val coord: Coordinates = Coordinates(),
    val weather: List<Weather> = listOf(),
    val main: MainData = MainData(),
    val wind: Wind = Wind(),
    val sys: SystemData = SystemData()
)

data class Coordinates(
    val lon: Double = 0.0,
    val lat: Double = 0.0
)

data class Weather(
    val main: String = "",
    val description: String = ""
)

data class MainData(
    val temp: Double = 0.0,
    val humidity: Int = 0
)

data class Wind(
    val speed: Double = 0.0,
    val deg: Int = 0
)

data class SystemData(
    val sunrise: Long = 0L,
    val sunset: Long = 0L,
    val country: String = ""
)