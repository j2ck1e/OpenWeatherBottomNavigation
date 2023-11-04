package com.jcdesign.openweatherbottomnavigation.repository

import com.jcdesign.openweatherbottomnavigation.api.RetrofitInstance
import com.jcdesign.openweatherbottomnavigation.db.WeatherDatabase

class WeatherRepository(
    val db: WeatherDatabase
) {
    suspend fun getWeather() =
        RetrofitInstance.api.getWeather()

}