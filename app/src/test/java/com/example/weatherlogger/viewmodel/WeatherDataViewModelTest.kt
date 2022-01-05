package com.example.weatherlogger.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weatherlogger.db.currentweatherdata.CurrentWeatherData
import com.example.weatherlogger.network.responses.Coordinates
import com.example.weatherlogger.repository.repositoryinterface.LocationRepositoryInterface
import com.example.weatherlogger.repository.repositoryinterface.WeatherDataRepositoryInterface
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when` as whenever

class WeatherDataViewModelTest {

    // Executes each task synchronously using Architecture Components.
    @get:Rule var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: WeatherDataViewModel
    private lateinit var locationRepository: LocationRepositoryInterface
    private lateinit var weatherDataRepository: WeatherDataRepositoryInterface

    @Before
    fun setup() {
        locationRepository = mock(LocationRepositoryInterface::class.java)
        weatherDataRepository = mock(WeatherDataRepositoryInterface::class.java)
        viewModel = WeatherDataViewModel(weatherDataRepository, locationRepository)
    }

    @Test
    fun `Get All Weather Data`() = runBlocking {
        whenever(weatherDataRepository.getAllWeatherData()).thenReturn(
            flow {
                emit(
                    listOf(
                        CurrentWeatherData(
                            temperature = 10.5,
                            date = 123L)))
            }
        )

        val weatherDataFlow = viewModel.getAllWeatherData()

        assertEquals(10.5, weatherDataFlow.first().first().temperature, 0.0)
        assertEquals(123L, weatherDataFlow.first().first().date)
    }

    @Test
    fun `Get Weather Data by date`() = runBlocking {
        whenever(weatherDataRepository.getWeatherDataByDate(123L)).thenReturn(
            flow {
                emit(
                    CurrentWeatherData(
                        temperature = 10.5,
                        date = 123L))
            }
        )

        val weatherDataFlow = viewModel.getWeatherDataByDate(123L)

        assertEquals(10.5, weatherDataFlow.first().temperature, 0.0)
        assertEquals(123L, weatherDataFlow.first().date)
    }

    @Test
    fun `Get Weather Data by non existing date return empty flow`() = runBlocking {
        whenever(weatherDataRepository.getWeatherDataByDate(133L)).thenReturn(
            flow {
                emit(
                    CurrentWeatherData(
                        temperature = 10.5,
                        date = 123L))
            }
        )

        val weatherDataFlow = viewModel.getWeatherDataByDate(123L)

        assertEquals(null, weatherDataFlow)
    }

    @Test
    fun `save current Weather Data`() = runBlocking {
        whenever(locationRepository.getCurrentLocation()).thenReturn(
            Coordinates(31.5, 30.6)
        )

        whenever(weatherDataRepository.saveCurrentWeatherData(30.6, 31.5)).thenReturn(Unit)

        viewModel.saveCurrentWeatherData()

        verify(locationRepository).getCurrentLocation()
        verify(weatherDataRepository).saveCurrentWeatherData(30.6, 31.5)
    }
}
