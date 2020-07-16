package com.muhamad.weatherlogger.view.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.muhamad.weatherlogger.model.WeatherModel
import java.lang.reflect.Type

class DataConverter {

    @TypeConverter
    fun fromWeatherBeanList(weatherBean: List<WeatherModel.WeatherBean?>?): String? {
        if (weatherBean == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<WeatherModel.WeatherBean?>?>() {}.type
        return gson.toJson(weatherBean, type)
    }

    @TypeConverter
    fun toWeatherBeanList(weatherBeanString: String?): List<WeatherModel.WeatherBean>? {
        if (weatherBeanString == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<WeatherModel.WeatherBean?>?>() {}.type
        return gson.fromJson<List<WeatherModel.WeatherBean>>(weatherBeanString, type)
    }

    @TypeConverter
    fun fromWindBean(windBean: WeatherModel.WindBean?): String? {
        if (windBean == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<WeatherModel.WindBean?>() {}.type
        return gson.toJson(windBean, type)
    }

    @TypeConverter
    fun toWindBean(windBeanString: String?): WeatherModel.WindBean? {
        if (windBeanString == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<WeatherModel.WindBean?>() {}.type
        return gson.fromJson<WeatherModel.WindBean>(windBeanString, type)
    }

    @TypeConverter
    fun fromMainBean(mainBean: WeatherModel.MainBean?): String? {
        if (mainBean == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<WeatherModel.MainBean?>() {}.type
        return gson.toJson(mainBean, type)
    }

    @TypeConverter
    fun toMainBean(mainBeanString: String?): WeatherModel.MainBean? {
        if (mainBeanString == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<WeatherModel.MainBean?>() {}.type
        return gson.fromJson<WeatherModel.MainBean>(mainBeanString, type)
    }

    @TypeConverter
    fun fromSysBean(sysBean: WeatherModel.SysBean?): String? {
        if (sysBean == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<WeatherModel.SysBean?>() {}.type
        return gson.toJson(sysBean, type)
    }

    @TypeConverter
    fun toSysBean(sysBeanString: String?): WeatherModel.SysBean? {
        if (sysBeanString == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<WeatherModel.SysBean?>() {}.type
        return gson.fromJson<WeatherModel.SysBean>(sysBeanString, type)
    }
}