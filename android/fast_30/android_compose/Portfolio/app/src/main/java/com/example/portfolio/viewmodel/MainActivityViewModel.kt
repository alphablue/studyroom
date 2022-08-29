package com.example.portfolio.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Looper
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.google.android.gms.location.*
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    @ApplicationContext context: Context,
    dispatcherProvider: DispatcherProvider
): BaseViewModel(dispatcherProvider) {
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    private val defaultLocationRequestClient = LocationRequest.create().apply {
        priority = Priority.PRIORITY_HIGH_ACCURACY
        interval = 500
    }

    private val locationCallback = object: LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            _userLocation = result.lastLocation
        }
    }

    private var _userLocation by mutableStateOf<Location?>(null)
    val userLocation: Location?
        get() = _userLocation

    @SuppressLint("MissingPermission")
    fun startLocationUpdate(
        locationRequestClient: LocationRequest = defaultLocationRequestClient,
        locationCallback: LocationCallback = this.locationCallback,
    ) {
        fusedLocationClient.requestLocationUpdates(locationRequestClient, locationCallback, Looper.getMainLooper())
    }

    fun stopLocationUpdate() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    @SuppressLint("MissingPermission")
    fun getLocation() {
        fusedLocationClient.lastLocation.addOnCompleteListener {
            _userLocation = it.result
        }
    }
}