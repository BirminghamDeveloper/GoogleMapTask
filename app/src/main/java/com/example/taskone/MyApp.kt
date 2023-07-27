package com.example.taskone

import android.app.Application
import androidx.room.Room
import com.example.taskone.db.LocationDataBase

class MyApp : Application() {

    companion object DatabaseSetup {
        var database: LocationDataBase? = null
    }

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(
            this,
            LocationDataBase::class.java,
            "location-database"
        ).allowMainThreadQueries().build()
    }
}
