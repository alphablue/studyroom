package com.example.studystartingpoint.systemArch.gpsModule

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.studystartingpoint.util.d
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class GpsWorker(
    val context: Context,
    val params: WorkerParameters
): CoroutineWorker(context, params) {
    private val GPS_INTERVAL_MILLI = 1000L

    private val fusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }

    private val locationRequest = LocationRequest
        .Builder(Priority.PRIORITY_HIGH_ACCURACY, GPS_INTERVAL_MILLI)
        .build()

    private val locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            for (location in locationResult.locations) {
                "worker 에서 위치 정보 호출 $location".d("locationWorker")
            }
        }
    }

    @Suppress("MissingPermission")
    override suspend fun doWork(): Result {

        withContext(Dispatchers.IO) {
            repeat(15) {
                delay(500)
                "run on gps worker".d("locationWorker")
            }
        }

        /**
         * 장기 실행을 보장하는 worker 를 만들 수 있기는 하지만 결국에는 foreground 를 설정해야 한다.
         * */
//        setForeground()

        return Result.success()
    }

}