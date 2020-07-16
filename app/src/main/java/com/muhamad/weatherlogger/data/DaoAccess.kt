package com.muhamad.weatherlogger.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.muhamad.weatherlogger.model.WeatherModel

@Dao
interface DaoAccess {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(item: WeatherModel?): Long

    @Query("SELECT * FROM WeatherModel")
    fun fetchAllItems(): LiveData<List<WeatherModel?>?>?

    @Query("SELECT COUNT() FROM WeatherModel WHERE id = :id")
    fun getItem(id: String?): Int

    @Delete
    fun deleteItem(item: WeatherModel?)
}