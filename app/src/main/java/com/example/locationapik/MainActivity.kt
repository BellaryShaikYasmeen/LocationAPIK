package com.example.locationapik

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.Toast
import com.google.android.gms.common.api.GoogleApi
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteFragment
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener

class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private var mGoogleMap:GoogleMap?=null
private lateinit var autoccompleteFragment:AutocompleteSupportFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Places.initialize(this,getString(R.string.apiKey))
        autoccompleteFragment=supportFragmentManager.findFragmentById(R.id.auto_complete_fragemnet)
        as AutocompleteSupportFragment
        autoccompleteFragment.setPlaceFields(listOf(Place.Field.ID,Place.Field.ADDRESS,Place.Field.LAT_LNG))
        autoccompleteFragment.setOnPlaceSelectedListener(object :PlaceSelectionListener{
            override fun onError(p0: Status) {
             Toast.makeText(this@MainActivity,"Some Error in Search",Toast.LENGTH_LONG).show()
            }

            override fun onPlaceSelected(p0: Place) {

                val latling=p0.latLng!!
                zoomIn(latling)
            }

        })
        val mapFragment = supportFragmentManager.findFragmentById(
            R.id.maps_fragment
        ) as? SupportMapFragment
        mapFragment?.getMapAsync (this)
      val mapbtn:ImageButton=findViewById(R.id.mapOptionsMenu)
        val popmenu=PopupMenu(this,mapbtn)
        popmenu.menuInflater.inflate(R.menu.map_option,popmenu.menu)
        popmenu.setOnMenuItemClickListener {menuItem->
            changeMenuitem(menuItem.itemId)
            true
        }
        mapbtn.setOnClickListener {
            popmenu.show()
        }
    }
    fun zoomIn(latling:LatLng)
    {
        val newLatLing=CameraUpdateFactory.newLatLngZoom(latling,12f)
        mGoogleMap?.animateCamera(newLatLing)
    }

    private fun changeMenuitem(itemid: Int) {
        when(itemid)
        {
            R.id.normal->mGoogleMap?.mapType=GoogleMap.MAP_TYPE_NORMAL
            R.id.hybrid->mGoogleMap?.mapType=GoogleMap.MAP_TYPE_HYBRID
            R.id.satellite->mGoogleMap?.mapType=GoogleMap.MAP_TYPE_SATELLITE
            R.id.terrain->mGoogleMap?.mapType=GoogleMap.MAP_TYPE_TERRAIN

        }

    }

    override fun onMapReady(p0: GoogleMap) {
        mGoogleMap=p0
    }
}