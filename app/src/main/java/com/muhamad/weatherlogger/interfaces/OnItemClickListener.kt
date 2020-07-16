package com.muhamad.weatherlogger.interfaces

import android.view.View
import com.muhamad.weatherlogger.model.WeatherModel

interface OnItemClickListener {

    fun onMoreClick(weatherModel: WeatherModel, view: View)

    fun onDetailsClick(weatherModel: WeatherModel)

}