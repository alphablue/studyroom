package com.example.portfolio.ui.screen.util

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Looper
import android.util.Log
import com.google.android.gms.location.*
import kotlinx.coroutines.delay

@SuppressLint("MissingPermission")
fun getMyLocation(context: Context, getLocation: (Location) -> Unit) {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    val locationRequestClient = LocationRequest.create().apply {
        priority = Priority.PRIORITY_HIGH_ACCURACY
        interval = 500
    }
    val locationCallBack = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            for (location in locationResult.locations) {
                getLocation(location)
                Log.d("locationTest", location.toString() + "location call back")
            }
        }
    }
    fusedLocationClient.requestLocationUpdates(locationRequestClient, locationCallBack, Looper.getMainLooper())

    fusedLocationClient.lastLocation.addOnCompleteListener {
        task ->
        getLocation(task.result)
        Log.d("locationTest", task.result.toString() + "fused location")
    }


    fusedLocationClient.removeLocationUpdates(locationCallBack)

}