package com.jcdesign.openweatherbottomnavigation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jcdesign.openweatherbottomnavigation.R
import com.jcdesign.openweatherbottomnavigation.databinding.ListItemBinding
import com.jcdesign.openweatherbottomnavigation.models.DetailWeather

class WeatherAdapter : RecyclerView.Adapter<WeatherAdapter.DetailWeatherHolder>() {

    inner class DetailWeatherHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ListItemBinding.bind(itemView)
    }

    private val differCallback = object : DiffUtil.ItemCallback<DetailWeather>() {
        override fun areItemsTheSame(oldItem: DetailWeather, newItem: DetailWeather): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DetailWeather, newItem: DetailWeather): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailWeatherHolder {
        return DetailWeatherHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((DetailWeather) -> Unit)? = null
    override fun onBindViewHolder(holder: DetailWeatherHolder, position: Int) {
        val detailWeather = differ.currentList[position]
        holder.binding.apply {
            tvDate.text = detailWeather.dt_txt
            tvCloud.text = "clouds: ${detailWeather.clouds.all.toString()}%"
            tvHumidity.text = "humidity: ${detailWeather.main.humidity.toString()}%"
            tvPressure.text = "${detailWeather.main.pressure.toString()} hpa"
            tvTemp.text = "Temperature from ${detailWeather.main.temp_min} to ${detailWeather.main.temp_max} ºC"
            tvWind.text = "wind: ${detailWeather.wind.speed.toString()} m/s"

            cvItem.setOnClickListener() {
                onItemClickListener?.let { it(detailWeather)}
            }

        }
    }



    fun setOnItemClickListener(listener: (DetailWeather) -> Unit){
        onItemClickListener = listener
    }


}

