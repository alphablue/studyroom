package com.example.studystartingpoint.systemArch.gpsModule

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.provider.Settings
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.studystartingpoint.ui.CommonDesignedComponent.Button.VerticalSpaceButton
import com.example.studystartingpoint.util.RequestGpsPermissionAlert
import com.example.studystartingpoint.util.RequestNotificationPermissionAlert
import com.example.studystartingpoint.util.checkLocationPermissionAndList
import com.example.studystartingpoint.util.d
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.google.android.gms.location.Priority

val locationCallback = object : LocationCallback() {
    override fun onLocationResult(location: LocationResult) {
        "fusedLocationClient 에서 update 요청 값 받음 $location".d("locationInfo")
    }
}

@Composable
fun GpsRunEntryPoint(
    paddingValues: PaddingValues
) {
    val context = LocalContext.current
    var locationPermissionState by remember { mutableStateOf(false) }
    var notificationPermissionState by remember { mutableStateOf(false) }

    Column(
        Modifier
            .padding(paddingValues)
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        VerticalSpaceButton(12.dp) {
            Button(
                modifier = Modifier
                    .wrapContentWidth()
                    .height(36.dp),
                shape = RoundedCornerShape(4.dp),
                onClick = {
                    locationPermissionState = true
                }
            ) {
                Text("GPS 권한 요청 하기")
            }
        }

        VerticalSpaceButton(12.dp, {
            Button(
                modifier = Modifier
                    .wrapContentWidth()
                    .height(36.dp),
                shape = RoundedCornerShape(4.dp),
                onClick = {
                    @SuppressLint("MissingPermission")
                    runGpsCollect(
                        context = context,
                        callBackRequestPermission = { locationPermissionState = it }
                    )

//                    testOption(
//                        context = context,
//                        locationManager = (context as Activity).getSystemService(Context.LOCATION_SERVICE) as LocationManager
//                    )
                }
            ) {
                Text("GPS 실행 하기")
            }
        })

        VerticalSpaceButton(12.dp, {
            Button(
                modifier = Modifier
                    .wrapContentWidth()
                    .height(36.dp),
                shape = RoundedCornerShape(4.dp),
                onClick = {
//                    stopGpsCollect(activity = context as Activity)
                    context.startForegroundService(
                        Intent(context, GpsBackground::class.java)
                            .apply {
                                action = GpsBackground.ACTION_STOP_GPS
                            })
                }
            ) {
                Text("GPS 중단 요청")
            }
        })

        VerticalSpaceButton(12.dp, {
            Button(
                modifier = Modifier
                    .wrapContentWidth()
                    .height(36.dp),
                shape = RoundedCornerShape(4.dp),
                onClick = {
                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                        notificationPermissionState = true
                    } else {
                        context.startForegroundService(Intent(context, GpsBackground::class.java))
//                    context.startService(Intent(context, GpsBackground::class.java))
                    }
                }
            ) {
                Text("포그라운드 서비스 권한 요청")
            }
        })

        VerticalSpaceButton(12.dp, {
            Button(
                modifier = Modifier
                    .wrapContentWidth()
                    .height(36.dp),
                shape = RoundedCornerShape(4.dp),
                onClick = {
                    context.startForegroundService(
                        Intent(context, GpsBackground::class.java)
                            .apply {
                                action = GpsBackground.ACTION_START_GPS
                            })
//                    context.startService(Intent(context, GpsBackground::class.java))
                }
            ) {
                Text("GPS 백그라운드 서비스 실행")
            }
        })
    }

    if (locationPermissionState) {
        RequestGpsPermissionAlert(
            requiredBackground = true,
            completeCallback = { locationPermissionState = false }
        )
    }

    if (notificationPermissionState) {
        RequestNotificationPermissionAlert { notificationPermissionState = false }
    }
}

const val LOCATION_POPUP_CALL_CODE = 787878

@RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
fun runGpsCollect(
    context: Context,
    callBackRequestPermission: (Boolean) -> Unit = {}
) {
    val activity = context as? Activity
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    val locationManager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    /**
     * 다른 앱에서 위치정보를 활용한 경우, 남아 있는 가장 최신 정보를 가져온다.
     * */
    fusedLocationClient.lastLocation
        .addOnSuccessListener {
            "[gms location] 마지막 위치 정보 가져옴 $it".d("locationInfo")
        }

    /**
     * 위치 요청의 우선순위를 설정합니다. 우선순위는 위치 서비스가 사용할 위치 정보 출처와 전력 소모량에 영향을 미칩니다.
     *
     * - `PRIORITY_BALANCED_POWER_ACCURACY`:
     *   - 정확도: 약 100미터 (도시 블록 수준)
     *   - 전력 소모: 낮음
     *   - 설명: WiFi 및 기지국 위치 정보를 사용할 가능성이 높습니다.
     *          대략적인 정확도 수준으로, 전력 소모를 줄이는 데 중점을 둡니다.
     *
     * - `PRIORITY_HIGH_ACCURACY`:
     *   - 정확도: 가장 높음
     *   - 전력 소모: 높음
     *   - 설명: GPS를 사용하여 가장 정확한 위치를 확인합니다.
     *
     * - `PRIORITY_LOW_POWER`:
     *   - 정확도: 약 10km (도시 수준)
     *   - 전력 소모: 매우 낮음
     *   - 설명: 대략적인 정확도 수준으로, 전력 소모를 최소화합니다.
     *
     * - `PRIORITY_PASSIVE`:
     *   - 정확도: 다양함 (다른 앱의 요청에 따라 다름)
     *   - 전력 소모: 최소화
     *   - 설명: 앱에서 직접 위치 업데이트를 트리거하지 않고, 다른 앱에서 트리거한 위치 정보를 수신합니다.
     *          전력 소비에 미치는 영향이 거의 없습니다.
     * */
    val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000L)
        .setMinUpdateIntervalMillis(300L)
        .build()

    /**
     * 위치 정보를 받기위해서 사용자가 위치 정보 옵션을 켰는지에 대한 상태를 확인 하는 기능을 제공 한다.
     * gps 기능의 on/off 를 확인함
     * */
    val locationBuilder = LocationSettingsRequest.Builder()
        .addLocationRequest(locationRequest)
    val client = LocationServices.getSettingsClient(context)
    val task = client.checkLocationSettings(locationBuilder.build())

    task.addOnCompleteListener { task ->
        try {
            /**
             * gps 설정 on/off의 단일 정보만 알고 싶다면 아래것만 사용하면 됨
             * */
//            task.addOnSuccessListener { response ->
//                "위치 정보를 위한 설정 상태 확인 [성공] $response".d("locationInfo")
//            }

            val response = task.getResult(ApiException::class.java)
            "툴바의 GPS 옵션 설정 확인 [성공] ${response.locationSettingsStates}".d("locationInfo")

            /**
             * 위치 정보 권한 확인
             * */
            val checkLocationAndList = checkLocationPermissionAndList(context)

            if (checkLocationAndList.isNotEmpty()) {
                callBackRequestPermission(true)
            } else {
                /**
                 * 위치 정보 업데이트 요청
                 * */
//                locationManager.requestLocationUpdates(
//                    LocationManager.NETWORK_PROVIDER,
//                    1000L,
//                    10f
//                ) {
//                    "locationManager 에서 update 요청 값 받음 $it".d("locationInfo")
//                }

                fusedLocationClient.requestLocationUpdates(
                    locationRequest,
                    locationCallback,
                    context.mainLooper
                )
            }
        } catch (exception: ApiException) {
            /**
             * gps off 일때만 실행 하고 싶을때 아래 코드 활용
             * */
//            task.addOnFailureListener { exception ->
//                "위치 정보를 위한 설정 상태 확인 [실패] $exception".d("locationInfo")
//
//                if (exception is ResolvableApiException) {
//                    try {
//                        /**
//                         * 위치 옵션을 위한 안내 팝업이 노출됨
//                         * 해당 팝업에서 작업에 대한 유저의 반응은 onActivityResult 에서 처리 가능함 설정할 때 사용한 코드 값과 일치해야 확인 가능
//                         * resultCode 는 Activity.RESULT_OK, Activity.RESULT_CANCELED 로 넘어옴
//                         * */
////                exception.startResolutionForResult(activity!!, 1001)
//                    } catch (_: Exception) {
//
//                    }
//                }
//            }

            when (exception.statusCode) {
                LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                    "툴바의 GPS 옵션 설정 확인 [실패] 팝업 노출 가능".d("locationInfo")

                    /**
                     * google service 에서 제공하는 gps 설정 가능한 팝업 실행
                     * */
                    if (exception is ResolvableApiException) {
                        exception.startResolutionForResult(activity, LOCATION_POPUP_CALL_CODE)
                    }

                    /**
                     * 유저에게 직접 gps 옵션을 실행 시키려면 화면 이동을 시켜줘야함
                     * */
                    context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                }

                LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                    "툴바의 GPS 옵션 설정 확인 [실패] 팝업 노출 x".d("locationInfo")
                }
            }
        }
    }
}

fun stopGpsCollect(
    activity: Activity
) {
    "gps 업데이트 요청 취소".d("locationInfo")
    LocationServices.getFusedLocationProviderClient(activity).removeLocationUpdates(locationCallback)
}

fun testOption(
    context: Context,
    locationManager: LocationManager
) {
    // 구글에서 제공하는 확인 함수가 아닌 기본 함수에서 확인
    if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
        "gps 옵션이 켜져 있습니다.".d("locationInfo")
    } else {
        "gps 옵션이 꺼져 있습니다.".d("locationInfo")
    }
}

@Composable
fun GpsOptionAlert() {

}