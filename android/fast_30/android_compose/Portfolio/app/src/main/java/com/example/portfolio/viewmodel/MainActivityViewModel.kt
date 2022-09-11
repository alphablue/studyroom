package com.example.portfolio.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.portfolio.model.googlegeocode.GoogleGeoCode
import com.example.portfolio.repository.GoogleRepository
import com.google.android.gms.location.*
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val googleRepository: GoogleRepository,
    dispatcherProvider: DispatcherProvider
): BaseViewModel(dispatcherProvider) {
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    private val defaultLocationRequestClient = LocationRequest.create().apply {
        priority = Priority.PRIORITY_HIGH_ACCURACY
        interval = 5000
    }

    private val locationCallback = object: LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            _realTimeUserLocation = result.lastLocation
        }
    }

    var testAPICallCount = 0
    var splitAddress by mutableStateOf("위치 정보를 찾을 수 없습니다.")

    // 위도 경도 데이터를 관찰하는 부분
    private var _realTimeUserLocation by mutableStateOf<Location?>(null)
    val realTimeUserLocation: Location?
        get() = _realTimeUserLocation

    private var _geocodeState by mutableStateOf<GoogleGeoCode?>(null)
    val geocodeState: GoogleGeoCode?
        get() = _geocodeState

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
    fun getLocation(callback: (location: Location) -> Unit) {
        fusedLocationClient.lastLocation.addOnCompleteListener {
            callback(it.result)
        }
    }


    fun getReverseGeoCode(
        returnType: String = "json",
        lat: Double,
        lng: Double
    ) = onIO {
        try {
            val reverseData = googleRepository.getReverseGeoCodeData(returnType, lat, lng)
            _geocodeState = reverseData

            testAPICallCount += 1
            Log.d("MainActivityViewModel", "reverseGeoCode get data :: $testAPICallCount")
        } catch (e: Exception) {
            Log.d("MainActivityViewModel", "reverseGeoCode error : ${e.message}")
        }
    }

    fun getAddress() {

        fun reverseGeoCodeCallBack(lastLocation: Location) =
            lastLocation.let {
                getReverseGeoCode(lat = it.latitude, lng = it.longitude)

                geocodeState?.let { addData->
                    splitAddress = addData.results.first()
                        .formattedAddress
                        .split(" ")
                        .filterIndexed { index, _ ->
                            index > 1
                        }
                        .joinToString(" ")
                }
            } ?: run { splitAddress = "위치정보 조회중" }

        getLocation{
            reverseGeoCodeCallBack(it)
        }

        testAPICallCount += 1
    }
}