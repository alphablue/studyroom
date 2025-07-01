package com.example.studystartingpoint.systemArch.gpsModule

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import com.example.studystartingpoint.R
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices

class GpsBackground : Service(), LocationListener {

    private var fusedLocationClient: FusedLocationProviderClient? = null
    private var locationCallback: LocationCallback? = null
    private var locationManager: LocationManager? = null

    companion object { // NOSONAR
        const val NOTIFICATION_CHANNEL_ID = "gps_background_channel"
        const val NOTIFICATION_ID = 1
        const val ACTION_START_GPS = "com.example.studystartingpoint.ACTION_START_GPS"
        const val ACTION_STOP_GPS = "com.example.studystartingpoint.ACTION_STOP_GPS"
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        startForeground(NOTIFICATION_ID, createNotification())

        if (isGooglePlayServicesAvailable()) {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    locationResult ?: return
                    for (location in locationResult.locations) {
                        processLocation(location)
                    }
                }
            }
        } else {
            locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        }
    }

    private fun processLocation(location: Location) {
        // GPS 정보 수신 시 처리할 로직
        Log.d("GpsBackground", "위도: ${location.latitude}, 경도: ${location.longitude}")
        // TODO: 수집된 GPS 정보를 서버로 전송하거나 로컬에 저장하는 등의 작업 수행
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START_GPS -> startLocationUpdates()
            ACTION_STOP_GPS -> stopLocationUpdates()
        }
        return START_STICKY // 서비스가 강제 종료되어도 시스템이 다시 시작하려고 시도
    }

    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (isGooglePlayServicesAvailable() && fusedLocationClient != null && locationCallback != null) {
                val locationRequest = LocationRequest.create().apply {
                    interval = 10000 // 10초마다 위치 업데이트
                    fastestInterval = 5000 // 가장 빠른 업데이트 간격 5초
                    priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                }
                fusedLocationClient!!.requestLocationUpdates(locationRequest, locationCallback!!, Looper.getMainLooper())
                Log.d("GpsBackground", "Fused GPS 업데이트 시작")
            } else if (locationManager != null) {
                // GPS 프로바이더 사용 가능 여부 확인
                val isGpsEnabled = locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)
                // 네트워크 프로바이더 사용 가능 여부 확인
                val isNetworkEnabled = locationManager!!.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

                if (isGpsEnabled) {
                    locationManager!!.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000L, 0f, this)
                    Log.d("GpsBackground", "Standard GPS (GPS_PROVIDER) 업데이트 시작")
                } else if (isNetworkEnabled) {
                    locationManager!!.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000L, 0f, this)
                    Log.d("GpsBackground", "Standard GPS (NETWORK_PROVIDER) 업데이트 시작")
                } else {
                    Log.e("GpsBackground", "사용 가능한 위치 제공자 없음 (Standard)")
                    stopSelf()
                }
            } else {
                Log.e("GpsBackground", "위치 서비스 초기화 실패")
                stopSelf()
            }
        } else {
            Log.e("GpsBackground", "ACCESS_FINE_LOCATION 권한 없음")
            stopSelf()
        }
    }

    private fun stopLocationUpdates() {
        fusedLocationClient?.removeLocationUpdates(locationCallback!!)
        locationManager?.removeUpdates(this)
        Log.d("GpsBackground", "GPS 업데이트 중지")
        stopForeground(STOP_FOREGROUND_REMOVE) // API 24 이상에서는 STOP_FOREGROUND_REMOVE 사용
        stopSelf()
    }

    override fun onBind(intent: Intent?): IBinder? = null // 바인딩 사용 안 함

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "GPS Background Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }

    private fun createNotification(): Notification {
        return NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setContentTitle("GPS 서비스 실행 중")
            .setContentText("백그라운드에서 위치 정보를 수집하고 있습니다.")
            .setSmallIcon(R.drawable.ic_launcher_foreground) // TODO: 적절한 아이콘으로 변경
            .build()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopLocationUpdates() // 서비스 종료 시 위치 업데이트 중지 보장
    }

    private fun isGooglePlayServicesAvailable(): Boolean {
        val googleApiAvailability = GoogleApiAvailability.getInstance()
        val resultCode = googleApiAvailability.isGooglePlayServicesAvailable(this)
        return resultCode == ConnectionResult.SUCCESS
    }

    // LocationListener 인터페이스 구현 (Google Play 서비스 미사용 시)
    override fun onLocationChanged(location: Location) {
        processLocation(location)
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        // 위치 공급자의 상태 변경 시 호출 (API 레벨 29 이하에서 사용)
    }

    override fun onProviderEnabled(provider: String) {
        Log.d("GpsBackground", "$provider 사용 가능")
    }

    override fun onProviderDisabled(provider: String) {
        Log.d("GpsBackground", "$provider 사용 불가능")
    }
}