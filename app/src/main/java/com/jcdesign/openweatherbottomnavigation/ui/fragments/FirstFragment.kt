package com.jcdesign.openweatherbottomnavigation.ui.fragments

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
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
import com.jcdesign.openweatherbottomnavigation.ui.WeatherViewModel
import com.jcdesign.openweatherbottomnavigation.ui.WeatherViewModelProviderFactory
import com.jcdesign.openweatherbottomnavigation.util.Constants.Companion.REQUEST_CODE_LOCATION_PERMISSION
import com.jcdesign.openweatherbottomnavigation.util.LocationUtility
import com.jcdesign.openweatherbottomnavigation.util.Resource
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions


class FirstFragment : Fragment(), EasyPermissions.PermissionCallbacks {
    private lateinit var binding: FragmentFirstBinding
    private lateinit var viewModel: WeatherViewModel
    private lateinit var weatherAdapter: WeatherAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestPermissions()

        val weatherRepository = WeatherRepository(WeatherDatabase(requireContext()))
        val viewModelProviderFactory =
            WeatherViewModelProviderFactory(requireActivity().application, weatherRepository)
        viewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(WeatherViewModel::class.java)

        setupRecyclerView()

        getDetailWeather()


        weatherAdapter.setOnItemClickListener {
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
                        Toast.makeText(activity, "An error occured: $message", Toast.LENGTH_LONG)
                            .show()

                    }
                    getSavedDetailWeather()
                }

                is Resource.Loading -> {
                    showProgressBar()
                }
            }

        })
    }

    private fun requestPermissions() {
        if (LocationUtility.hasLocationPermissions(requireContext())) {
            return
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            EasyPermissions.requestPermissions(
                this,
                "You need to accept location permissions to use this app.",
                REQUEST_CODE_LOCATION_PERMISSION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        } else {
            EasyPermissions.requestPermissions(
                this,
                "You need to accept location permissions to use this app.",
                REQUEST_CODE_LOCATION_PERMISSION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            )
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        } else {
            requestPermissions()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {}

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }


}