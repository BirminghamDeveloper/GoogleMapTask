package com.example.taskone.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.taskone.model.SavedLocation

@Database(entities = [SavedLocation::class], version = 1)
@TypeConverters(value = [Converters::class])
abstract class LocationDataBase : RoomDatabase() {

    abstract fun getLocationDao(): LocationDao

}


