package com.jcdesign.openweatherbottomnavigation.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jcdesign.openweatherbottomnavigation.repository.WeatherRepository

class WeatherViewModelProviderFactory(
    val app: Application,
    val weatherRepository: WeatherRepository
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WeatherViewModel(app, weatherRepository) as T
    }

}