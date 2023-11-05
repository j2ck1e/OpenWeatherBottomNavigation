package com.jcdesign.openweatherbottomnavigation.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jcdesign.openweatherbottomnavigation.R
import com.jcdesign.openweatherbottomnavigation.adapters.WeatherAdapter
import com.jcdesign.openweatherbottomnavigation.databinding.FragmentFirstBinding
import com.jcdesign.openweatherbottomnavigation.db.WeatherDatabase
import com.jcdesign.openweatherbottomnavigation.repository.WeatherRepository
import com.jcdesign.openweatherbottomnavigation.ui.MainActivity
import com.jcdesign.openweatherbottomnavigation.ui.WeatherViewModel
import com.jcdesign.openweatherbottomnavigation.ui.WeatherViewModelProviderFactory
import com.jcdesign.openweatherbottomnavigation.util.Resource


class FirstFragment : Fragment() {
    private lateinit var binding: FragmentFirstBinding
    private lateinit var viewModel: WeatherViewModel
    private lateinit var weatherAdapter: WeatherAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("MyLog", "First fragment")
        binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val weatherRepository = WeatherRepository(WeatherDatabase(requireContext()))
        val viewModelProviderFactory = WeatherViewModelProviderFactory(weatherRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(WeatherViewModel::class.java)

        setupRecyclerView()


        viewModel.weather.observe(viewLifecycleOwner, Observer { response ->
            when(response) {
                is Resource.Success -> {
                    response.data?.let {weatherResponse ->
                        weatherAdapter.differ.submitList(weatherResponse.list)
                    }
                }
                is Resource.Error -> {
                    response.message?.let {message ->
                        Log.d("MyLog", "An error occured: $message")

                    }
                }
                is Resource.Loading -> {
                    Log.d("MyLog", "Loading...")
                }
            }

        })

        weatherAdapter.setOnItemClickListener {
            Log.d("MyLog", "onClick!!!")
            val bundle = Bundle().apply {
                putSerializable("detailWeather", it)
            }
            findNavController().navigate(
                R.id.action_firstFragment_to_detailFragment,
                bundle
            )

        }
    }

    private fun setupRecyclerView(){
        weatherAdapter = WeatherAdapter()
        binding.rcWeather.apply {
            adapter = weatherAdapter
            layoutManager = LinearLayoutManager(activity)
        }
        Log.d("MyLog", "weatherAdapter: $weatherAdapter")
    }



}