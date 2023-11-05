package com.jcdesign.openweatherbottomnavigation.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jcdesign.openweatherbottomnavigation.R
import com.jcdesign.openweatherbottomnavigation.adapters.WeatherAdapter
import com.jcdesign.openweatherbottomnavigation.databinding.FragmentFirstBinding
import com.jcdesign.openweatherbottomnavigation.db.WeatherDatabase
import com.jcdesign.openweatherbottomnavigation.models.DetailWeather
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
        val viewModelProviderFactory = WeatherViewModelProviderFactory(requireActivity().application,weatherRepository)
        viewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(WeatherViewModel::class.java)

        setupRecyclerView()


//      getSavedDetailWeather()
        getDetailWeather()


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

    private fun setupRecyclerView() {
        weatherAdapter = WeatherAdapter()
        binding.rcWeather.apply {
            adapter = weatherAdapter
            layoutManager = LinearLayoutManager(activity)
        }
        Log.d("MyLog", "weatherAdapter: $weatherAdapter")
    }

    private fun hideProgressBar() {
        binding.paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.paginationProgressBar.visibility = View.VISIBLE
    }

    private fun saveDetailWeather(listOfDetailWeather: List<DetailWeather>) {
        viewModel.saveDetailWeather(listOfDetailWeather)
    }

    private fun getSavedDetailWeather() {
        showProgressBar()
        viewModel.getSavedDetailWeather().observe(viewLifecycleOwner, Observer { detailWeather ->
            Log.d("MyLog", "getSavedData $detailWeather")
            weatherAdapter.differ.submitList(detailWeather)
            hideProgressBar()
        })
    }

    private fun getDetailWeather() {
        viewModel.weather.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { weatherResponse ->
                        weatherAdapter.differ.submitList(weatherResponse.list)
                        saveDetailWeather(weatherResponse.list)
                    }
                }

                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Toast.makeText(activity, "An error occured: $message", Toast.LENGTH_LONG).show()

                    }
                    getSavedDetailWeather()
                }

                is Resource.Loading -> {
                    showProgressBar()
                    Log.d("MyLog", "Loading...")

                }
            }

        })
    }


}