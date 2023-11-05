package com.jcdesign.openweatherbottomnavigation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.jcdesign.openweatherbottomnavigation.databinding.FragmentDetailBinding
import com.jcdesign.openweatherbottomnavigation.db.WeatherDatabase
import com.jcdesign.openweatherbottomnavigation.repository.WeatherRepository
import com.jcdesign.openweatherbottomnavigation.ui.WeatherViewModel
import com.jcdesign.openweatherbottomnavigation.ui.WeatherViewModelProviderFactory


class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private lateinit var viewModel: WeatherViewModel
    val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val weatherRepository = WeatherRepository(WeatherDatabase(requireContext()))
        val viewModelProviderFactory = WeatherViewModelProviderFactory(requireActivity().application, weatherRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(WeatherViewModel::class.java)

        val detailWeather = args.detailWeather
        binding.apply {
            detailDate.text = detailWeather.dt_txt
            detailCloud.text = "clouds: ${detailWeather.clouds.all.toString()}%"
            detailHumidity.text = "humidity: ${detailWeather.main.humidity.toString()}%"
            detailPressure.text = "${detailWeather.main.pressure.toString()} hpa"
            detailTemp.text = "Temperature from ${detailWeather.main.temp_min} to ${detailWeather.main.temp_max} ºC"
            detailWind.text = "wind: ${detailWeather.wind.speed.toString()} m/s"
        }

    }


}