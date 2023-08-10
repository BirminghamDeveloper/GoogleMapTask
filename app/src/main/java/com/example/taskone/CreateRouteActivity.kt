package com.example.taskone

import android.content.Intent
import android.graphics.Color
import android.graphics.Point
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import com.example.taskone.db.LocationDao
import com.example.taskone.model.LatLngList

import com.example.taskone.model.SavedLocation
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*

private const val TAG = "CreateRouteActivity"
class CreateRouteActivity : AppCompatActivity(), OnMapReadyCallback, View.OnTouchListener {

    private var supportMapFragment: SupportMapFragment? = null
    private var mGoogleMap: GoogleMap? = null
    private var mMapShelterView: View? = null
    private var mGestureDetector: GestureDetector? = null
    private val mLatlngs = ArrayList<LatLng>()
    private var mPolylineOptions: PolylineOptions? = null
    private var mPolygonOptions: PolygonOptions? = null

    // flag to differentiate whether user is touching to draw or not
    private var mDrawFinished = false
    private lateinit var locationDao: LocationDao
    private var listLatLng = ArrayList<LatLng>()
    private var locationExtra: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        locationDao = MyApp.database?.getLocationDao()!!
        val btnSave = findViewById<Button>(R.id.btnSave)
        locationExtra = intent.getIntExtra("saved Locations Extra", 0)
        mMapShelterView = findViewById(R.id.drawer_view) as View
        mGestureDetector = GestureDetector(this, GestureListener())
        mMapShelterView!!.setOnTouchListener(this)

        supportMapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        supportMapFragment!!.getMapAsync {
            mGoogleMap = it
        }


        btnSave.setOnClickListener {
            if (!listLatLng.isNullOrEmpty()) {
                var list = LatLngList(listLatLng)
                locationDao.saveLocation(SavedLocation(list))
                startActivity(Intent(this,MenuActivity::class.java))
                this.finish()
            }else{
                Toast.makeText(this, "please select the route", Toast.LENGTH_SHORT).show()
            }
        }
    }


    override fun onMapReady(it: GoogleMap) {
        mGoogleMap = it
        if (locationExtra == 0){
            mMapShelterView?.isClickable = true
        }else{

            mMapShelterView?.isClickable = false
            mDrawFinished = true
            val list = getLocationListById(locationExtra!!)
            mGoogleMap!!.clear()
            mPolylineOptions = null
            mMapShelterView!!.visibility = View.GONE
            mGoogleMap!!.uiSettings.isZoomGesturesEnabled = true
            mGoogleMap!!.uiSettings.setAllGesturesEnabled(true)
            mPolygonOptions = PolygonOptions()
            mPolygonOptions?.fillColor(Color.GRAY)
            mPolygonOptions?.strokeColor(Color.RED)
            mPolygonOptions?.strokeWidth(5f)
            mPolygonOptions?.addAll(list.latlngList.latLngList)
            mGoogleMap!!.addPolygon(mPolygonOptions!!)
        }
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        var X1 = event?.x?.toInt()
        var Y1 = event?.y?.toInt()
        var point = Point()
        point.x = X1!!
        point.y = Y1!!
        val firstGeoPoint = mGoogleMap!!.projection.fromScreenLocation(point)
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {}
            MotionEvent.ACTION_MOVE -> if (mDrawFinished) {
                X1 = event.x.toInt()
                Y1 = event.y.toInt()
                point = Point()
                point.x = X1
                point.y = Y1
                val geoPoint = mGoogleMap!!.projection
                    .fromScreenLocation(point)
                mLatlngs.add(geoPoint)
                mPolylineOptions = PolylineOptions()
                mPolylineOptions?.color(Color.RED)
                mPolylineOptions?.width(5f)
                mPolylineOptions?.addAll(mLatlngs)
                mGoogleMap!!.addPolyline(mPolylineOptions!!)
                listLatLng = mLatlngs
            }
            MotionEvent.ACTION_UP -> {
                mLatlngs.add(firstGeoPoint)
                mGoogleMap!!.clear()
                mPolylineOptions = null
                mMapShelterView!!.visibility = View.GONE
                mGoogleMap!!.uiSettings.isZoomGesturesEnabled = true
                mGoogleMap!!.uiSettings.setAllGesturesEnabled(true)
                mPolygonOptions = PolygonOptions()
                mPolygonOptions?.fillColor(Color.GRAY)
                mPolygonOptions?.strokeColor(Color.RED)
                mPolygonOptions?.strokeWidth(5f)
                mPolygonOptions?.addAll(mLatlngs)
                mGoogleMap!!.addPolygon(mPolygonOptions!!)
                mDrawFinished = false
            }
        }

        return mGestureDetector?.onTouchEvent(event!!)!!

    }

    fun drawZone(view: View?) {
        mGoogleMap!!.clear()
        mLatlngs.clear()
        mPolylineOptions = null
        mPolygonOptions = null
        mDrawFinished = true
        mMapShelterView!!.visibility = View.VISIBLE
        mGoogleMap!!.uiSettings.isScrollGesturesEnabled = false
    }

    class GestureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onDown(p0: MotionEvent): Boolean {
            return true
        }

        override fun onFling(p0: MotionEvent, p1: MotionEvent, p2: Float, p3: Float): Boolean {
            return false
        }
    }

    private fun getLocationListById(id: Int): SavedLocation {
        val location = locationDao.getlocation(locationExtra!!)
        return location
    }
}



