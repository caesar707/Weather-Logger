package com.muhamad.weatherlogger.model.api

import com.muhamad.weatherlogger.model.WeatherModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {

    @Headers("Accept: application/json")
    @GET("weather")
    fun getWeatherData(
        @Query("lat") latitude: String?,
        @Query("lon") longitude: String?,
        @Query("appid") key: String?
    ): Call<WeatherModel>?
}