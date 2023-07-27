package com.example.taskone.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.taskone.model.SavedLocation
import com.google.android.gms.maps.model.LatLng


@Dao
interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveLocation(locationList : SavedLocation)

    @Query("select * from savedLocations")
    fun getSavedLocation():LiveData<List<SavedLocation>>


    @Query("select * from savedLocations where id = :mId ")
    fun getlocation(mId: Int):SavedLocation

}