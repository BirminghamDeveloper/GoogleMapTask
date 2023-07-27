package com.example.taskone.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng
import java.io.Serializable

@Entity(tableName = "savedLocations")

data class SavedLocation(
    var latlngList : LatLngList
){
    @PrimaryKey(autoGenerate = true)
    var id :Int ? = null
}
