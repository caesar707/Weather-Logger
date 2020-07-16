package com.muhamad.weatherlogger.view.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Resources
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.muhamad.weatherlogger.R
import com.muhamad.weatherlogger.databinding.ActivityDetailsBinding
import com.muhamad.weatherlogger.model.WeatherModel
import com.muhamad.weatherlogger.view.utils.Constants

class DetailsActivity : AppCompatActivity() {

    private lateinit var detailsBinding: ActivityDetailsBinding
    private lateinit var model: WeatherModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_details)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (intent != null) {
            model = intent.getSerializableExtra("model") as WeatherModel
            updateViews()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateViews() {

        detailsBinding.tvTemp.text = String.format("%.0f", (model.getMain()!!.temp - 273.15)) + " ْ C"

        detailsBinding.tvCity.text = model.getName()
        detailsBinding.tvDesc.text = model.getWeather()?.get(0)?.main
        detailsBinding.tvDate.text = "last update: " + Constants.getDate(model.getDt()*1000)

        detailsBinding.tvPressure.text = model.getMain()!!.pressure.toString() + " hpa"
        detailsBinding.tvHumidity.text = model.getMain()!!.humidity.toString() + " %"

        detailsBinding.tvWind.text = model.getWind()!!.speed.toString() + " m/s"

        detailsBinding.tvSunrise.text = Constants.getTime(model.getSys()!!.sunrise*1000)
        detailsBinding.tvSunset.text = Constants.getTime(model.getSys()!!.sunset*1000)

        detailsBinding.isDay = Constants.isDay(model.getDt()*1000)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            this.finish()
        return super.onOptionsItemSelected(item)
    }

    override fun startActivity(intent: Intent?) {
        super.startActivity(intent)
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out)
    }
}