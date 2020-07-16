package com.muhamad.weatherlogger.view.utils

import android.R
import android.annotation.SuppressLint
import android.content.Context
import java.text.SimpleDateFormat
import java.util.*


class Constants {
    companion object {
        const val BASE_URL = "http://api.openweathermap.org/data/2.5/"
        const val REQUEST_TIMEOUT_DURATION = 10
        const val DEBUG = true
        const val APP_ID = "97d7955e2ab96a1af79b8a4baef15fc8"

        @SuppressLint("SimpleDateFormat")
        fun getDate(date: Long) : String {

            val df = SimpleDateFormat("dd MMM yyy hh:mm aa")
            return df.format(Date(date))
        }

        @SuppressLint("SimpleDateFormat")
        fun getTime(date: Long) : String {

            val df = SimpleDateFormat("hh:mm aa")
            return df.format(Date(date))
        }

        fun isDay(date: Long) : Boolean {

            val calendar =  Calendar.getInstance()
            calendar.time = Date(date)

            val hour = calendar.get(Calendar.HOUR_OF_DAY)

            return hour in 5..19
        }

    }


}