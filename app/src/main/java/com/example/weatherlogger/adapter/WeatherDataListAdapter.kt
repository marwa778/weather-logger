package com.example.weatherlogger.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherlogger.databinding.WeatherDataItemBinding
import com.example.weatherlogger.db.currentweatherdata.CurrentWeatherData
import java.text.SimpleDateFormat
import java.util.*

class WeatherDataListAdapter(
    private val onItemClicked: (CurrentWeatherData) -> Unit
) : ListAdapter<CurrentWeatherData, WeatherDataListAdapter.WeatherDataViewHolder>(DiffCallback) {

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<CurrentWeatherData>() {
            override fun areItemsTheSame(oldItem: CurrentWeatherData, newItem: CurrentWeatherData): Boolean {
                return oldItem.date == newItem.date
            }

            override fun areContentsTheSame(oldItem: CurrentWeatherData, newItem: CurrentWeatherData): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherDataViewHolder {
        val viewHolder = WeatherDataViewHolder(
            WeatherDataItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            onItemClicked(getItem(position))
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: WeatherDataViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class WeatherDataViewHolder(
        private var binding: WeatherDataItemBinding
    ): RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SimpleDateFormat")
        fun bind(currentWeatherData: CurrentWeatherData) {
            binding.temperature.text = currentWeatherData.temperature.toString()
            binding.date.text = SimpleDateFormat(
                "h:mm a").format(
                Date(currentWeatherData.date * 1000)
            )
        }
    }
}
