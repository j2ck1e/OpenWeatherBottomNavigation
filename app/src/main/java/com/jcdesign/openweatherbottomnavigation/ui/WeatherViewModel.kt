package com.jcdesign.openweatherbottomnavigation.ui

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.TYPE_ETHERNET
import android.net.ConnectivityManager.TYPE_MOBILE
import android.net.ConnectivityManager.TYPE_WIFI
import android.net.NetworkCapabilities.TRANSPORT_CELLULAR
import android.net.NetworkCapabilities.TRANSPORT_ETHERNET
import android.net.NetworkCapabilities.TRANSPORT_WIFI
import android.os.Build
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jcdesign.openweatherbottomnavigation.WeatherApplication
import com.jcdesign.openweatherbottomnavigation.models.DetailWeather
import com.jcdesign.openweatherbottomnavigation.models.WeatherResponse
import com.jcdesign.openweatherbottomnavigation.repository.WeatherRepository
import com.jcdesign.openweatherbottomnavigation.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class WeatherViewModel(
    app: Application,
    val weatherRepository: WeatherRepository
) : AndroidViewModel(app) {

    val weather: MutableLiveData<Resource<WeatherResponse>> = MutableLiveData()

    init {
        getWeather()
    }

    fun getWeather() = viewModelScope.launch {
        safeDetailWeatherCall()
//        weather.postValue(Resource.Loading())
//        val response = weatherRepository.getWeather()
//        weather.postValue(handleWeatherResponse(response))
    }

    private fun handleWeatherResponse(response: Response<WeatherResponse>): Resource<WeatherResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)

            }
        }
        return Resource.Error(response.message())
    }

    fun saveDetailWeather(listOfDetailWeather: List<DetailWeather>) = viewModelScope.launch {
        weatherRepository.upsert(listOfDetailWeather)
    }

    fun getSavedDetailWeather() = weatherRepository.getSavedDetailWeather()

    private suspend fun safeDetailWeatherCall() {
        weather.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()) {
                val response = weatherRepository.getWeather()
                weather.postValue(handleWeatherResponse(response))
            } else {
                weather.postValue(Resource.Error("No internet connection"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> weather.postValue(Resource.Error("Network failure"))
                else -> weather.postValue(Resource.Error("Conversion error"))
            }
        }
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<WeatherApplication>()
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(TRANSPORT_WIFI) -> true
                capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when (type) {
                    TYPE_WIFI -> true
                    TYPE_MOBILE -> true
                    TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }

}