package com.muhamad.weatherlogger.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class WeatherModel : Serializable{

    private var main: MainBean? = null
    private var wind: WindBean? = null
    private var sys: SysBean? = null
    private var dt: Long = 0
    @PrimaryKey
    private var eventDate: Long = 0

    private var id = 0
    private var name: String? = null
    private var weather: List<WeatherBean?>? = null

    fun getEventDate(): Long {
        return eventDate
    }

    fun setEventDate(event_date: Long) {
        this.eventDate = event_date
    }

    fun getMain(): MainBean? {
        return main
    }

    fun setMain(main: MainBean?) {
        this.main = main
    }

    fun getSys(): SysBean? {
        return sys
    }

    fun setSys(sys: SysBean?) {
        this.sys = sys
    }

    fun getWind(): WindBean? {
        return wind
    }

    fun setWind(wind: WindBean?) {
        this.wind = wind
    }

    fun getDt(): Long {
        return dt
    }

    fun setDt(dt: Long) {
        this.dt = dt
    }

    fun getId(): Int {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getName(): String? {
        return name
    }

    fun setName(name: String?) {
        this.name = name
    }

    fun getWeather(): List<WeatherBean?>? {
        return weather
    }

    fun setWeather(weather: List<WeatherBean?>?) {
        this.weather = weather
    }

    class MainBean : Serializable {
        var temp = 0.0
        var feels_like = 0.0
        var temp_min = 0.0
        var temp_max = 0.0
        var pressure = 0
        var humidity = 0
    }

    class SysBean : Serializable {
        var country: String? = null
        var sunrise: Long = 0
        var sunset: Long = 0
    }

    class WindBean : Serializable {
        var speed = 0.0
        var deg = 0

    }

    class WeatherBean : Serializable {
        var id = 0
        var main: String? = null
        var description: String? = null
        var icon: String? = null

    }
}