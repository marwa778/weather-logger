package com.example.weatherlogger.db.currentweatherdata

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrentWeatherDataDao {
    @Query("SELECT * FROM weather_data WHERE date = :date")
    fun getWeatherDataByDate(date: Long): Flow<CurrentWeatherData>

    @Query("SELECT * FROM weather_data")
    fun getAllWeatherData(): Flow<List<CurrentWeatherData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplaceWeatherData(currentWeatherData: CurrentWeatherData)
}
