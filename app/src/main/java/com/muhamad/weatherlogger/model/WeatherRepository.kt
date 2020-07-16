package com.muhamad.weatherlogger.model

import com.muhamad.weatherlogger.model.api.ApiClient
import com.muhamad.weatherlogger.view.utils.Constants.Companion.APP_ID
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherRepository {

    fun getWeatherData(lat: String, lon: String ,
                       onResult: (isSuccess: Boolean, response: WeatherModel?) -> Unit) {

        ApiClient.instance.getWeatherData(lat, lon, APP_ID)?.enqueue(object : Callback<WeatherModel>{
            override fun onResponse(call: Call<WeatherModel>?, response: Response<WeatherModel>?) {
                if (response != null && response.isSuccessful){
                    onResult(true, response.body()!!)
                } else {
                    onResult(false, null)
                }
            }

            override fun onFailure(call: Call<WeatherModel>?, t: Throwable) {
                onResult(false, null)
            }
        })

    }

    companion object {
        private var INSTANCE: WeatherRepository? = null
        fun getInstance() = INSTANCE
            ?: WeatherRepository().also {
                INSTANCE = it
            }
    }
}