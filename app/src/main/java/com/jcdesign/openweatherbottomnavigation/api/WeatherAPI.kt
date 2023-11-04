package com.jcdesign.openweatherbottomnavigation.api

import com.jcdesign.openweatherbottomnavigation.models.WeatherResponse
import com.jcdesign.openweatherbottomnavigation.util.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {

    @GET("data/2.5/forecast")
    suspend fun getWeather(
        @Query("q")
        city: String = "London",
        @Query("appid")
        apiKey: String = API_KEY,
        @Query("units")
        unit: String  = "metric"
    ): Response<WeatherResponse>
}