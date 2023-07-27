package com.example.taskone.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.taskone.R
import com.example.taskone.model.SavedLocation

class LocationAdapter(val mlist: List<SavedLocation> , val onItemClick: OnItemClick) : RecyclerView.Adapter<LocationAdapter.ViewHolder>() {


    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.location_list_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mlist.get(position)
        holder.textView.text = "Location # ${position +1}"

        holder.itemView.setOnClickListener {
            onItemClick.onClicked(item,item.id!!)
        }

    }


    override fun getItemCount() = mlist.size


    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val textView: TextView = itemView.findViewById(R.id.tvLocationNumber)


    }

}