package com.muhamad.weatherlogger.view.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.*
import com.muhamad.weatherlogger.R
import com.muhamad.weatherlogger.data.ItemRepository
import com.muhamad.weatherlogger.databinding.ActivityMainBinding
import com.muhamad.weatherlogger.interfaces.OnItemClickListener
import com.muhamad.weatherlogger.model.WeatherModel
import com.muhamad.weatherlogger.view.adapter.WeatherListAdapter
import com.muhamad.weatherlogger.view_model.WeatherViewModel


class MainActivity : AppCompatActivity(), OnItemClickListener {

    private lateinit var activityMainBinding: ActivityMainBinding

    private lateinit var adapter: WeatherListAdapter

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback

    private lateinit var viewModel: WeatherViewModel

    private var eventDate: Long = 0

    private val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 400

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setUpAdapter()
        setUpObservers()

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        getLocationUpdates()
        getLocationPermission()
    }

    private fun setUpAdapter() {

        adapter = WeatherListAdapter(this)
        activityMainBinding.mainRecycle.adapter = adapter
    }

    private fun setUpObservers() {

        viewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)

        viewModel.weatherDataLive.observe(this, Observer {
            activityMainBinding.animationView.visibility = View.GONE
            if (it != null) {
                it.setEventDate(eventDate)
                ItemRepository.getInstance(this).insertItem(it)
            } else {
                Toast.makeText(this, "Can't fetch weather data!", Toast.LENGTH_LONG).show()
            }
        })

        ItemRepository.getInstance(this).items?.observe(this, Observer {
            adapter.updateRepoList(it)
        })
    }

    private fun getLocationPermission() {

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            checkLocation()
        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
            )
        }
    }

    private fun checkLocation(){
        val manager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            showAlertLocation()
        }
    }

    private fun showAlertLocation() {
        val dialog = AlertDialog.Builder(this)
        dialog.setMessage("Your location settings is set to Off, Please enable location " +
                "to use this application")
        dialog.setPositiveButton("Settings") { _, _ ->
            val myIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(myIntent)
        }
        dialog.setNegativeButton("Cancel") { _, _ ->
            finish()
        }
        dialog.setCancelable(false)
        dialog.show()
    }

    private fun getLocationUpdates() {

        locationRequest = LocationRequest()
        locationRequest.interval = 50000
        locationRequest.fastestInterval = 50000
        locationRequest.smallestDisplacement = 170f //170 m
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                if (locationResult.locations.isNotEmpty()) {
                    val location = locationResult.lastLocation
                    Log.e("location", location.toString())
                    viewModel.fetchWeatherData(location.latitude.toString(), location.longitude.toString())
                }
            }
        }
    }

    // Start location updates
    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            null /* Looper */
        )
    }

    // Stop location updates
    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    // Stop receiving location update when activity not visible/foreground
    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String?>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // Permission was granted.
                if (ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                    checkLocation()
                }
            } else {
                // Permission denied, Disable the functionality that depends on this permission.
                Toast.makeText(this, "Location Permission Denied!", Toast.LENGTH_SHORT).show()
                finishAffinity()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.save) {
            activityMainBinding.animationView.visibility = View.VISIBLE
            eventDate = System.currentTimeMillis()
            startLocationUpdates()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onMoreClick(weatherModel: WeatherModel, view: View) {
        val popupMenu: PopupMenu = PopupMenu(this, view)
        popupMenu.menuInflater.inflate(R.menu.popup_menu,popupMenu.menu)
        popupMenu.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.delete -> {
                    ItemRepository.getInstance(this).deleteItem(weatherModel)
                    Toast.makeText(this, "Item Successfully Deleted!", Toast.LENGTH_LONG).show()
                }
            }
            true
        }
        popupMenu.show()
    }

    override fun onDetailsClick(weatherModel: WeatherModel) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra("model", weatherModel)
        startActivity(intent)
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