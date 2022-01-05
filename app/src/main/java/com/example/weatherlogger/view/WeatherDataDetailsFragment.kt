package com.example.weatherlogger.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.coroutineScope
import com.example.weatherlogger.databinding.FragmentWeatherDataDetailsBinding
import com.example.weatherlogger.db.currentweatherdata.CurrentWeatherData
import com.example.weatherlogger.viewmodel.WeatherDataViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class WeatherDataDetailsFragment : Fragment() {

    @Inject
    lateinit var viewModel: WeatherDataViewModel

    private var _binding: FragmentWeatherDataDetailsBinding? = null

    private val binding get() = _binding!!

    private lateinit var temperatureTextView: AppCompatTextView
    private lateinit var dateTextView: AppCompatTextView
    private lateinit var descriptionTextView: AppCompatTextView
    private lateinit var nameTextView: AppCompatTextView
    private lateinit var countryTextView: AppCompatTextView
    private lateinit var sunsetTextView: AppCompatTextView
    private lateinit var sunriseTextView: AppCompatTextView
    private lateinit var windSpeedTextView: AppCompatTextView
    private lateinit var humidityTextView: AppCompatTextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWeatherDataDetailsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews()
        val date = arguments?.get("weatherDate") as Long
        lifecycle.coroutineScope.launch {
            viewModel.getWeatherDataByDate(date).collect {
                setText(it)
            }
        }
    }

    private fun bindViews() {
        countryTextView = binding.detailsCountry
        nameTextView = binding.detailsName
        descriptionTextView = binding.detailsDescription
        sunriseTextView = binding.detailsSunrise
        sunsetTextView = binding.detailsSunset
        temperatureTextView = binding.detailsTemperature
        dateTextView = binding.detailsDate
        humidityTextView = binding.detailsHumidity
        windSpeedTextView = binding.detailsWindSpeed
    }
    
    private fun setText(currentWeatherData: CurrentWeatherData) {
        countryTextView.text = currentWeatherData.country
        nameTextView.text = currentWeatherData.name
        descriptionTextView.text = currentWeatherData.description
        temperatureTextView.text = currentWeatherData.temperature.toString()
        humidityTextView.text = currentWeatherData.humidity.toString()
        windSpeedTextView.text = currentWeatherData.speed.toString()
        sunriseTextView.text = formatData(currentWeatherData.sunrise)
        sunsetTextView.text = formatData(currentWeatherData.sunset)
        dateTextView.text = formatData(currentWeatherData.date)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun formatData(date: Long) =
        SimpleDateFormat(
            "dd-MM-yyyy  h:mm a").format(
            Date(date * 1000)
        )
}