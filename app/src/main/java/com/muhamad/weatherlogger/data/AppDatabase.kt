package com.muhamad.weatherlogger.data

import android.content.Context
import androidx.room.*
import com.muhamad.weatherlogger.model.WeatherModel
import com.muhamad.weatherlogger.view.utils.DataConverter

@Database(entities = [WeatherModel::class], version = 3, exportSchema = false)
@TypeConverters(DataConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun daoAccess(): DaoAccess?

    companion object {
        private var INSTANCE: AppDatabase? = null
        fun getAppDatabase(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java, "items-database"
                )
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}