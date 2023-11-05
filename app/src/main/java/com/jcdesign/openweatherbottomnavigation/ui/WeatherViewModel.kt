package com.jcdesign.openweatherbottomnavigation.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jcdesign.openweatherbottomnavigation.models.DetailWeather
import com.jcdesign.openweatherbottomnavigation.models.WeatherResponse
import com.jcdesign.openweatherbottomnavigation.repository.WeatherRepository
import com.jcdesign.openweatherbottomnavigation.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class WeatherViewModel(
    val weatherRepository: WeatherRepository
): ViewModel() {

    val weather : MutableLiveData<Resource<WeatherResponse>> = MutableLiveData()

    init{
        getWeather()
    }

    fun getWeather() = viewModelScope.launch {
        weather.postValue(Resource.Loading())
        val response = weatherRepository.getWeather()
        weather.postValue(handleWeatherResponse(response))
    }

    private fun handleWeatherResponse(response: Response<WeatherResponse>) : Resource<WeatherResponse>{
        if(response.isSuccessful){
            response.body()?.let{ resultResponse ->
                return Resource.Success(resultResponse)

            }
        }
        return Resource.Error(response.message())
    }

    fun saveDetailWeather(listOfDetailWeather: List<DetailWeather>) = viewModelScope.launch {
        weatherRepository.upsert(listOfDetailWeather)
    }

    fun getSavedDetailWeather() = weatherRepository.getSavedDetailWeather()

}