package com.jcdesign.openweatherbottomnavigation.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.jcdesign.openweatherbottomnavigation.models.Clouds
import com.jcdesign.openweatherbottomnavigation.models.Main
import com.jcdesign.openweatherbottomnavigation.models.Wind

class Converters {

    @TypeConverter
    fun fromClouds(clouds: Clouds): String {
        val gson = Gson()
        return gson.toJson(clouds)
    }

    @TypeConverter
    fun toClouds(json: String): Clouds {
        val gson = Gson()
        return gson.fromJson(json, Clouds::class.java)
    }

    @TypeConverter
    fun fromMain(main: Main): String {
        val gson = Gson()
        return gson.toJson(main)
    }

    @TypeConverter
    fun toMain(json: String): Main {
        val gson = Gson()
        return gson.fromJson(json, Main::class.java)
    }

    @TypeConverter
    fun fromWind(wind: Wind): String {
        val gson = Gson()
        return gson.toJson(wind)
    }

    @TypeConverter
    fun toWind(json: String): Wind {
        val gson = Gson()
        return gson.fromJson(json, Wind::class.java)
    }
}