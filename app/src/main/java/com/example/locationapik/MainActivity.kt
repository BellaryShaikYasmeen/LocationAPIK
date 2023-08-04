package com.example.locationapik

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.PopupMenu
import com.google.android.gms.common.api.GoogleApi
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment

class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private var mGoogleMap:GoogleMap?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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