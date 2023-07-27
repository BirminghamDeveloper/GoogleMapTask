package com.example.taskone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import android.window.OnBackInvokedDispatcher
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskone.adapter.LocationAdapter
import com.example.taskone.adapter.OnItemClick
import com.example.taskone.db.LocationDao
import com.example.taskone.db.LocationDataBase
import com.example.taskone.model.SavedLocation
import com.google.android.gms.maps.model.LatLng
import okhttp3.OkHttp
import javax.security.auth.callback.Callback

class MenuActivity : AppCompatActivity() , OnItemClick{
    lateinit var mAdapter: LocationAdapter
    lateinit var locationDao: LocationDao
    lateinit var rvLocations: RecyclerView

    private lateinit var locationsLiveData: LiveData<List<SavedLocation>>
    

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        locationDao = MyApp.database?.getLocationDao()!!

        locationsLiveData = locationDao.getSavedLocation()

        val button = findViewById<Button>(R.id.btnCreate)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        rvLocations = findViewById(R.id.rvLocations)


        locationsLiveData.observe(this, Observer {
            setUpRecyclerView(it)
            Log.d("MenuActivity", "saved location livedata ${it.toString()}")
        })

        setSupportActionBar(toolbar)

        button.setOnClickListener {
            val intent = Intent(this,CreateRouteActivity::class.java)
            startActivity(intent)
        }


    }

    private fun setUpRecyclerView(list: List<SavedLocation>?) {
        mAdapter = LocationAdapter(list!!,this)
        rvLocations.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(this@MenuActivity,LinearLayoutManager.VERTICAL,false)
        }
    }

    override fun onClicked(location: SavedLocation, id: Int) {
        val intent = Intent(this,ShowRouteActivity::class.java)
        intent.putExtra("idFromMenuToShow",location.id)
        startActivity(intent)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    /*override fun getOnBackInvokedDispatcher(): OnBackInvokedDispatcher {
        return super.getOnBackInvokedDispatcher()
    }*/
}