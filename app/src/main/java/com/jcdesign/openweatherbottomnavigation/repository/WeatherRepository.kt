package com.jcdesign.openweatherbottomnavigation.repository

import com.jcdesign.openweatherbottomnavigation.api.RetrofitInstance
import com.jcdesign.openweatherbottomnavigation.db.WeatherDatabase
import com.jcdesign.openweatherbottomnavigation.models.DetailWeather

class WeatherRepository(
    val db: WeatherDatabase
) {
    suspend fun getWeather() =
        RetrofitInstance.api.getWeather()

    suspend fun upsert(listOfDetailWeather: List<DetailWeather>) = db.getWeatherDao().upsert(listOfDetailWeather)

    fun getSavedDetailWeather() = db.getWeatherDao().getDetailWeather()

}