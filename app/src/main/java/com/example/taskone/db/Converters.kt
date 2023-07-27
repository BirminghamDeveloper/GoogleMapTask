package com.example.taskone.db

import androidx.room.TypeConverter
import com.example.taskone.model.LatLngList
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson

class Converters {

    @TypeConverter
    fun fromLatLngToString(latLng: LatLng): String {
        return "(${latLng.latitude},${latLng.longitude})"
    }

    @TypeConverter
    fun fromStringToLatLng(string: String): LatLng {
        val s = string.replace("(", "").replace(")", "")
        val location = s.split(",")
        return LatLng(location.first().toDouble(),location.last().toDouble())
    }

    @TypeConverter
    fun fromLatLngToJSON(latLngList: LatLngList): String {
        return Gson().toJson(latLngList)
    }

    @TypeConverter
    fun fromJSONToClothing(json: String): LatLngList {
        return Gson().fromJson(json,LatLngList::class.java)
    }
}