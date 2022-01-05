package com.example.weatherlogger.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weatherlogger.db.currentweatherdata.CurrentWeatherData
import com.example.weatherlogger.db.currentweatherdata.CurrentWeatherDataDao

@Database(entities = [CurrentWeatherData::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun currentWeatherDataDao(): CurrentWeatherDataDao
}