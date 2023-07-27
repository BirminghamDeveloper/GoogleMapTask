package com.example.taskone.adapter

import com.example.taskone.model.SavedLocation

interface OnItemClick {
    fun onClicked(location: SavedLocation, id : Int)
}