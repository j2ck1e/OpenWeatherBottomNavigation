package com.jcdesign.openweatherbottomnavigation.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "weather"
)

data class DetailWeather(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    val clouds: Clouds,
//    val dt: Int,
    val dt_txt: String,
    val main: Main,
//    val pop: Double,
//    val rain: Rain,
//    val sys: Sys,
//    val visibility: Int,
//    val weather: List<Weather>,
    val wind: Wind
)