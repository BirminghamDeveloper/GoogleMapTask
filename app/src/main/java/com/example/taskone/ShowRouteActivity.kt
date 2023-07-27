package com.example.taskone

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.GestureDetector
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.taskone.db.LocationDao
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolygonOptions
import com.google.android.gms.maps.model.PolylineOptions


class ShowRouteActivity : AppCompatActivity() {


    private var supportMapFragment: SupportMapFragment? = null
    private var mGoogleMap: GoogleMap? = null
    private lateinit var locationDao: LocationDao


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_route)

        val locationExtra = intent.getIntExtra("idFromMenuToShow", 0)
        locationDao = MyApp.database?.getLocationDao()!!

        val btnEdit = findViewById<Button>(R.id.btnEdit)
        btnEdit.setOnClickListener {
            val intent = Intent(this,CreateRouteActivity::class.java)
            intent.putExtra("idFromShowToCreate",locationExtra)
            startActivity(intent)
        }

        val btnPlay = findViewById<Button>(R.id.btnPlay)
        btnPlay.setOnClickListener {
            val intent = Intent(this,PlayRouteActivity::class.java)
            intent.putExtra("idFromShowToPlay",locationExtra)
            startActivity(intent)
        }


        supportMapFragment = supportFragmentManager.findFragmentById(R.id.mapShow) as SupportMapFragment
        supportMapFragment!!.getMapAsync {
            mGoogleMap = it

            val list = locationDao.getlocation(locationExtra!!)
            mGoogleMap!!.clear()
            mGoogleMap!!.uiSettings.isZoomGesturesEnabled = true
            mGoogleMap!!.uiSettings.setAllGesturesEnabled(true)
            val mPolygonOptions = PolygonOptions()
            mPolygonOptions?.fillColor(Color.GRAY)
            mPolygonOptions?.strokeColor(Color.RED)
            mPolygonOptions?.strokeWidth(5f)
            mPolygonOptions?.addAll(list.latlngList.latLngList)
            mGoogleMap!!.addPolygon(mPolygonOptions!!)
        }


    }

}
