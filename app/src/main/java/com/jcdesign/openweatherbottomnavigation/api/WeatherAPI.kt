package com.jcdesign.openweatherbottomnavigation.api

import com.jcdesign.openweatherbottomnavigation.models.WeatherResponse
import com.jcdesign.openweatherbottomnavigation.util.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
//api.openweathermap.org/data/2.5/forecast?lat={lat}&lon={lon}&appid={API key}
interface WeatherAPI {

    @GET("data/2.5/forecast")
    suspend fun getWeather(
        @Query("lat")
        lat: String,
        @Query("lon")
        lon: String,
//        @Query("q")
//        city: String = "Tokio",
        @Query("appid")
        apiKey: String = API_KEY,
        @Query("units")
        unit: String  = "metric"
    ): Response<WeatherResponse>
}