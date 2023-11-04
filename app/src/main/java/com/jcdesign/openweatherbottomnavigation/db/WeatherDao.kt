package com.jcdesign.openweatherbottomnavigation.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jcdesign.openweatherbottomnavigation.models.DetailWeather


@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(detailWeather: DetailWeather): Long

    @Query("SELECT * FROM weather")
    fun getDetailWeather(): LiveData<List<DetailWeather>>

}