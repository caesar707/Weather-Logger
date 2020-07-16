package com.muhamad.weatherlogger.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.muhamad.weatherlogger.model.WeatherModel
import com.muhamad.weatherlogger.model.WeatherRepository

class WeatherViewModel : ViewModel() {

    val weatherDataLive = MutableLiveData<WeatherModel>()

    fun fetchWeatherData(lat: String, lon: String) {

        WeatherRepository.getInstance().getWeatherData(lat, lon) { isSuccess, response ->

            if (isSuccess) {
                weatherDataLive.value = response
            } else {
                weatherDataLive.value = null
            }

        }

    }
}