package com.example.weatherlogger.db

import android.content.Context
import androidx.room.Room
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4

import com.example.weatherlogger.db.currentweatherdata.CurrentWeatherDataDao
import com.example.weatherlogger.db.currentweatherdata.CurrentWeatherData
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert

@RunWith(AndroidJUnit4::class)
class WeatherDataDBTest {
    private lateinit var weatherDataDao: CurrentWeatherDataDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java).build()
        weatherDataDao = db.currentWeatherDataDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
     fun writeWeatherDataAndReadInList() = runBlocking {

        val currentWeatherData = CurrentWeatherData(
            temperature = 10.5,
            date = 123L
        )

        weatherDataDao.insertOrReplaceWeatherData(currentWeatherData)

        val allWeatherData = weatherDataDao.getAllWeatherData()

        Assert.assertEquals(10.5, allWeatherData.first().first().temperature, 0.0)
        Assert.assertEquals(123L, allWeatherData.first().first().date)


        val weatherDataFlow = weatherDataDao.getWeatherDataByDate(123L)

        Assert.assertEquals(10.5, weatherDataFlow.first().temperature, 0.0)
        Assert.assertEquals(123L, weatherDataFlow.first().date)
    }
}
