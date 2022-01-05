package com.example.weatherlogger.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.coroutineScope
import com.example.weatherlogger.R
import com.example.weatherlogger.databinding.FragmentWeatherDataDetailsBinding
import com.example.weatherlogger.viewmodel.WeatherDataViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class WeatherDataDetailsFragment : Fragment() {

    @Inject
    lateinit var viewModel: WeatherDataViewModel

    private var _binding: FragmentWeatherDataDetailsBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_weather_data_details, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val date = arguments?.get("weatherDate") as Long
        lifecycle.coroutineScope.launch {
            viewModel.getWeatherDataByDate(date).collect {
                binding.currentWeatherData = it
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}