package com.example.weatherlogger.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.coroutineScope
import com.example.weatherlogger.adapter.WeatherDataListAdapter
import com.example.weatherlogger.databinding.FragmentWeatherDataListBinding
import com.example.weatherlogger.viewmodel.WeatherDataViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class WeatherDataListFragment : Fragment() {

    @Inject
    lateinit var viewModel: WeatherDataViewModel

    private var _binding: FragmentWeatherDataListBinding? = null

    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWeatherDataListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.weatherDataList
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val weatherDataListAdapter = WeatherDataListAdapter({
            /*val action = FullScheduleFragmentDirections
                .actionFullScheduleFragmentToStopScheduleFragment(
                    stopName = it.stopName
                )
            view.findNavController().navigate(action)*/
        })
        recyclerView.adapter = weatherDataListAdapter

        lifecycle.coroutineScope.launch {
            viewModel.saveCurrentWeatherData()

            viewModel.getAllWeatherData().collect {
                weatherDataListAdapter.submitList(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}