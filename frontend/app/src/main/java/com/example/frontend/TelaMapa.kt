package com.example.frontend

//import com.example.frontend.utils.LocationPermissionHelper

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.mapbox.android.core.permissions.PermissionsListener
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.android.gestures.MoveGestureDetector
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.expressions.dsl.generated.interpolate
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.gestures.OnMoveListener
import com.mapbox.maps.plugin.gestures.gestures
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorBearingChangedListener
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener
import com.mapbox.maps.plugin.locationcomponent.location


//import kotlinx.coroutines.DefaultExecutor.key
//import kotlinx.coroutines.NonCancellable.key


/*
import androidx.fragment.app.FragmentContainerView
import com.tomtom.sdk.location.GeoLocation
import com.tomtom.sdk.location.OnLocationUpdateListener
//import com.tomtom.sdk.location.android.AndroidLocationProvider
import com.tomtom.sdk.map.display.MapOptions
import com.tomtom.sdk.map.display.TomTomMap
import com.tomtom.sdk.map.display.camera.CameraOptions
import com.tomtom.sdk.map.display.location.LocationMarkerOptions
import com.tomtom.sdk.map.display.ui.MapFragment*/

class TelaMapa : AppCompatActivity() {
    //private lateinit var tomTomMap : TomTomMap
    //private lateinit var locationProvider : AndroidLocationProvider

    private lateinit var mapView : MapView
    private lateinit var locationPermissionHelper: PermissionsManager

    var permissionsListener: PermissionsListener = object : PermissionsListener {
        override fun onExplanationNeeded(permissionsToExplain: List<String>) {

        }

        override fun onPermissionResult(granted: Boolean) {
            if (granted) {
                onMapReady()
                // Permission sensitive logic called here, such as activating the Maps SDK's LocationComponent to show the device's location

            } else {

                // User denied the permission

            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_mapa)
        mapView = findViewById(R.id.mapView)
        mapView?.getMapboxMap()?.loadStyleUri(Style.MAPBOX_STREETS)

        if(PermissionsManager.areLocationPermissionsGranted(this)) {
            onMapReady()
        }else {
            locationPermissionHelper = PermissionsManager(permissionsListener)
            locationPermissionHelper.requestLocationPermissions(this)
        }
    }

    override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

    private val onIndicatorBearingChangedListener = OnIndicatorBearingChangedListener {
        mapView.getMapboxMap().setCamera(CameraOptions.Builder().bearing(it).build())
    }

    private val onIndicatorPositionChangedListener = OnIndicatorPositionChangedListener {
        mapView.getMapboxMap().setCamera(CameraOptions.Builder().center(it).build())
        mapView.gestures.focalPoint = mapView.getMapboxMap().pixelForCoordinate(it)
    }

    private val onMoveListener = object : OnMoveListener {
        override fun onMoveBegin(detector: MoveGestureDetector) {
            //onCameraTrackingDismissed()
        }

        override fun onMove(detector: MoveGestureDetector): Boolean {
            return false
        }

        override fun onMoveEnd(detector: MoveGestureDetector) {}
    }

    private fun onMapReady() {
        mapView.getMapboxMap().setCamera(
            CameraOptions.Builder()
                .zoom(14.0)
                .build()
        )
        mapView.getMapboxMap().loadStyleUri(
            Style.MAPBOX_STREETS
        ) {
            //initLocationComponent()
            //setupGesturesListener()
        }
        //mapView.getMapboxMap().addOnMapClickListener(onMapClickListener)
        //setUpData()
    }


    /*private val onMapClickListener = object : OnMapClickListener {}
      fun onMapClick(point: LatLng) {

// Convert LatLng coordinates to screen pixel and only query the rendered features.
        val pixel: PointF = mapView.getMapboxMap().getProjection().toScreenLocation(point)
        val features: List<Feature> = mapView.getMapboxMap().queryRenderedFeatures(pixel)

// Get the first feature within the list if one exist
        if (features.size > 0) {
            val feature: Feature = features[0]

// Ensure the feature has properties defined
            if (feature.properties() != null) {
                for ((key, value): Map.Entry<String?, JsonElement?> in feature.properties()
                    .entrySet()) {
// Log all the properties
                    Log.d(TAG, String.format("%s = %s", key, value))
                }
            }
        }
    }

    private open fun setupSource(loadedStyle: Style) {
        source = GeoJsonSource(GEOJSON_SOURCE_ID)
        loadedStyle.addSource(source)
    }

    open fun setUpData() {
    if (mapboxMap != null) {
        mapboxMap.getStyle { style ->
            setupSource(style)
            setUpClickLocationIconImage(style)
            setUpClickLocationMarkerLayer(style)
            setUpInfoWindowLayer(style)
        }
    }*/
}



    /*private fun setupGesturesListener() {
        mapView.gestures.addOnMoveListener(onMoveListener)
    }

    private fun initLocationComponent() {
        val locationComponentPlugin = mapView.location
        locationComponentPlugin.updateSettings {
            this.enabled = true
            this.locationPuck = LocationPuck2D(
                bearingImage = AppCompatResources.getDrawable(
                    this@TelaMapa,
                    R.drawable.name_icon,
                ),
                shadowImage = AppCompatResources.getDrawable(
                    this@TelaMapa,
                    R.drawable.name_icon,
                ),
                scaleExpression = interpolate {
                    linear()
                    zoom()
                    stop {
                        literal(0.0)
                        literal(0.6)
                    }
                    stop {
                        literal(20.0)
                        literal(1.0)
                    }
                }.toJson()
            )
        }
        locationComponentPlugin.addOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
        locationComponentPlugin.addOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener)
    }

    private fun onCameraTrackingDismissed() {
        Toast.makeText(this, "onCameraTrackingDismissed", Toast.LENGTH_SHORT).show()
        mapView.location
            .removeOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
        mapView.location
            .removeOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener)
        mapView.gestures.removeOnMoveListener(onMoveListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.location
            .removeOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener)
        mapView.location
            .removeOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
        mapView.gestures.removeOnMoveListener(onMoveListener)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        locationPermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}*/
