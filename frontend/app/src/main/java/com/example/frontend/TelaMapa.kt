package com.example.frontend

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import com.tomtom.sdk.location.GeoLocation
import com.tomtom.sdk.location.OnLocationUpdateListener
import com.tomtom.sdk.location.android.AndroidLocationProvider
import com.tomtom.sdk.map.display.MapOptions
import com.tomtom.sdk.map.display.TomTomMap
import com.tomtom.sdk.map.display.camera.CameraOptions
import com.tomtom.sdk.map.display.location.LocationMarkerOptions
import com.tomtom.sdk.map.display.ui.MapFragment

class TelaMapa : AppCompatActivity() {
    private lateinit var tomTomMap : TomTomMap
    private lateinit var locationProvider : AndroidLocationProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_mapa)

        val mapOptions = MapOptions(mapKey = BuildConfig.API_KEY)
        val mapFragment = MapFragment.newInstance(mapOptions)

        supportFragmentManager.beginTransaction()
            .replace(R.id.map_container, mapFragment)
            .commit()

        mapFragment.getMapAsync { tomtomMap : TomTomMap ->
            tomTomMap = tomtomMap
            enableUserLocation()
            /*setUpMapListeners()
            showUserLocation()*/
        }
    }

    private fun setUpMapListeners() {

    }

    private fun enableUserLocation() {
        tomTomMap.isMyLocationEnabled = true
    }

    private fun showUserLocation() {
        locationProvider.enable()
        // zoom to current location at city level
        val onLocationUpdateListener = OnLocationUpdateListener { location ->
            tomTomMap.moveCamera(CameraOptions(location.position, zoom = 8.0))
        }
        locationProvider.addOnLocationUpdateListener(onLocationUpdateListener)
        tomTomMap.setLocationProvider(locationProvider)
        val locationMarker = LocationMarkerOptions(type = LocationMarkerOptions.Type.Pointer)
        tomTomMap.enableLocationMarker(locationMarker)
    }

}