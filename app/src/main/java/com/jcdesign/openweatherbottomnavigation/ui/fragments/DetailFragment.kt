package com.jcdesign.openweatherbottomnavigation.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.jcdesign.openweatherbottomnavigation.R
import com.jcdesign.openweatherbottomnavigation.databinding.FragmentDetailBinding
import com.jcdesign.openweatherbottomnavigation.databinding.FragmentFirstBinding
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
        val viewModelProviderFactory = WeatherViewModelProviderFactory(weatherRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(WeatherViewModel::class.java)

        val detailWeather = args.detailWeather
        binding.apply {
            tvText.text = detailWeather.dt_txt
        }

    }


}