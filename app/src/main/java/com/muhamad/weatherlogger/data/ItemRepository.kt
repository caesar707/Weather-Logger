package com.muhamad.weatherlogger.data

import android.annotation.SuppressLint
import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.muhamad.weatherlogger.model.WeatherModel
import com.muhamad.weatherlogger.model.WeatherRepository

class ItemRepository(context: Context?) {

    private val appDatabase: AppDatabase? = AppDatabase.getAppDatabase(context!!)

    @SuppressLint("StaticFieldLeak")
    fun insertItem(item: WeatherModel?) {
        object : AsyncTask<Void?, Void?, Void?>() {
            override fun doInBackground(vararg params: Void?): Void? {
                appDatabase!!.daoAccess()!!.insertItem(item)
                return null
            }
        }.execute()
    }

    @SuppressLint("StaticFieldLeak")
    fun deleteItem(item: WeatherModel?) {
        object : AsyncTask<Void?, Void?, Void?>() {
            override fun doInBackground(vararg params: Void?): Void? {
                appDatabase!!.daoAccess()!!.deleteItem(item)
                return null
            }
        }.execute()
    }

    fun isFavorite(itemId: String?): Boolean {
        return appDatabase!!.daoAccess()!!.getItem(itemId) == 1
    }

    val items: LiveData<List<WeatherModel?>?>?
        get() = appDatabase!!.daoAccess()!!.fetchAllItems()

    companion object {
        private var INSTANCE: ItemRepository? = null
        fun getInstance(context: Context?) = INSTANCE
            ?: ItemRepository(context).also {
                INSTANCE = it
            }
    }

}