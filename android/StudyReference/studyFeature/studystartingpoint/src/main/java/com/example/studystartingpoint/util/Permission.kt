package com.example.studystartingpoint.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

const val GPS_PERMISSION_REQUEST_CODE = 1001 // GPS 권한 요청을 위한 고유 코드

@Composable
fun RequestGpsPermissionAlert(
    requiredBackground: Boolean = false,
    completeCallback: () -> Unit // ui 의 노출 제어를 위해서 받아오는 과정이 필요함
) {
    val context = LocalContext.current
    val activity = context as? Activity
    val showPermissionDeniedDialog = remember { mutableStateOf(false) }
    val showRationaleDialog = remember { mutableStateOf(false) }
    val locationManager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    /**
     * gps, network 사용 가능 여부 활용이 필요한 경우 사용하기
     * */
    val checkGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    val checkNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

    /**
     * 단일 권한을 설정 할 때 사용 하는 런처
     * */
//    val prRequest = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {}

    val preRequestMulti = rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
        onMultiLocationPermissionResult(
            result,
            activity, requiredBackground,
            showPermissionDeniedDialog = { showPermissionDeniedDialog.value = it },
            showRationaleDialog = { showRationaleDialog.value = it },
            showPermissionDeniedDialogBackground = { showPermissionDeniedDialog.value = it },
            complete = { completeCallback() }
        )
    }

    /**
     * 실행 할 권한 리스트 받음
     * */
    val locationPermissionList = remember { checkLocationPermissionAndList(context, requiredBackground) }

    /**
     * 권한 실행
     * */
    LaunchedEffect(context) {
        preRequestMulti.launch(locationPermissionList.toTypedArray())
    }

    if (locationPermissionList.isEmpty()) {
        completeCallback()
        return
    }

    if (showRationaleDialog.value) {
        AlertDialog(
            onDismissRequest = {
                showRationaleDialog.value = false
                completeCallback()
            },
            title = { Text("권한 필요") },
            text = { Text("GPS 기능을 사용하기 위해서는 위치 권한이 필요합니다. 다시 요청하시겠습니까?") },
            confirmButton = {
                TextButton(onClick = {
                    showRationaleDialog.value = false
                    preRequestMulti.launch(locationPermissionList.toTypedArray())
                }) {
                    Text("다시 요청")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showRationaleDialog.value = false
                    completeCallback()
                }) {
                    Text("취소")
                }
            }
        )
    }

    if (showPermissionDeniedDialog.value) {
        AlertDialog(
            onDismissRequest = {
                showPermissionDeniedDialog.value = false
                completeCallback()
            },
            title = { Text("권한 거부됨") },
            text = { Text("위치 권한이 거부되었습니다. GPS 기능을 사용하려면 앱 설정에서 직접 권한을 허용해야 합니다.") },
            confirmButton = {
                TextButton(onClick = {
                    showPermissionDeniedDialog.value = false
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", context.packageName, null)
                    intent.data = uri
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

                    context.startActivity(intent)
                    completeCallback()
                }) {
                    Text("설정으로 이동")
                }
            }
        )
    }
}

fun onMultiLocationPermissionResult(
    result: Map<String, Boolean>,
    activity: Activity,
    checkBackgroundGpsPermission: Boolean = false,
    showPermissionDeniedDialog: (Boolean) -> Unit,
    showRationaleDialog: (Boolean) -> Unit,
    showPermissionDeniedDialogBackground: (Boolean) -> Unit,
    complete: () -> Unit
) {
    "위치 권한 허용 상태 확인 ${result.keys} : ${result.values}".d("locationInfo")

    // 나라별 "(xxx) 권한 허용" 을 위한 권한 팝업에서 옵션으로 노출되는 텍스트를 명칭을 가져온다. 한국의 경우 '항상 허용' 이 그에 해당하는 옵션이다.
//        val backgroundOptionLabel = context.packageManager.backgroundPermissionOptionLabel

//        "백그라운드 위치 권한 정보 $backgroundOptionLabel".d("locationInfo")

    // 권한 중 하나라도 거부된 경우
    if (result.values.any { granted -> !granted }) {
        // "다시 묻지 않음" 상태
        if (result.keys.any { permission ->
                "권한의 안내 다이얼로그 표출 여부 확인 ${!ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)}".d("locationInfo")
                !ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)
            }
        ) {
            showPermissionDeniedDialog(true)
        }
        // 단순 거부 상태
        else {
            showRationaleDialog(true)
        }
    }
    // background 위치 권한 확인
    else if (checkBackgroundGpsPermission) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            "백그라운드 위치 권한이 허용되지 않았습니다.".d("locationInfo")
            showPermissionDeniedDialogBackground(true)
        } else {
            complete()
        }
    }
    // 모든 조건 통과
    else {
        complete()
    }
}

fun checkLocationPermissionAndList(
    context: Context,
    optionBackground: Boolean = false
): List<String> {
    val locationPermissionList = mutableListOf<String>()

    val hasCoarseLocation = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
    val hasFineLocation = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    val hasBackgroundLocation = if (optionBackground) {
        ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
    } else true // 백그라운드 옵션이 꺼져있으면 항상 true로 간주

    if (hasCoarseLocation && hasFineLocation && hasBackgroundLocation) {
        "위치 정보에 대한 권한이 모두 있습니다.".d("locationInfo")
    } else {
        if (!hasCoarseLocation) {
            "대략적인 위치 정보에 대한 권한이 없습니다.".d("locationInfo")
            locationPermissionList.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
        if (!hasFineLocation) {
            "정확한 위치 정보에 대한 권한이 없습니다.".d("locationInfo")
            locationPermissionList.add(Manifest.permission.ACCESS_FINE_LOCATION)
        }
        if (optionBackground && !hasBackgroundLocation) {
            // 백그라운드 권한은 COARSE 또는 FINE 권한이 있어야 요청 의미가 있음
            // (안드로이드 시스템에서 COARSE/FINE 권한 없이는 BACKGROUND 권한 요청 다이얼로그가 뜨지 않거나, 의미없는 요청이 될 수 있음)
            // 또한, 이미 COARSE/FINE 권한이 요청 목록에 추가되었을 가능성이 높으므로,
            // 여기서는 백그라운드 권한만 추가할지, 아니면 COARSE/FINE도 함께 확인할지 정책에 따라 결정
            "백그라운드 위치 정보에 대한 권한이 없습니다.".d("locationInfo")
            locationPermissionList.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
        }
    }

    return locationPermissionList
}

@Composable
fun RequestNotificationPermissionAlert(
    onPermissionResult: (Boolean) -> Unit
) {
    val context = LocalContext.current
    val activity = context as? Activity
    val showPermissionDeniedDialog = remember { mutableStateOf(false) }
    val showRationaleDialog = remember { mutableStateOf(false) }

    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            "알림 권한 허용됨".d("NotificationPermission")
            onPermissionResult(true)
        } else {
            // 사용자가 권한을 거부한 경우
            if (activity != null && !ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.POST_NOTIFICATIONS)) {
                // "다시 묻지 않음"을 선택한 경우
                "알림 권한 '다시 묻지 않음' 선택됨".d("NotificationPermission")
                showPermissionDeniedDialog.value = true
            } else {
                // 단순 거부한 경우
                "알림 권한 단순 거부됨".d("NotificationPermission")
                showRationaleDialog.value = true
            }
            onPermissionResult(false)
        }
    }

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED -> {
                    // 이미 권한이 허용된 경우
                    "알림 권한 이미 허용됨".d("NotificationPermission")
                    onPermissionResult(true)
                }
                activity != null && ActivityCompat.shouldShowRequestPermissionRationale(
                    activity,
                    Manifest.permission.POST_NOTIFICATIONS
                ) -> {
                    // 이전에 거부했지만 "다시 묻지 않음"은 아닌 경우, 사용자에게 설명을 제공
                    "알림 권한 설명 필요".d("NotificationPermission")
                    showRationaleDialog.value = true
                }
                else -> {
                    // 권한을 요청
                    "알림 권한 요청".d("NotificationPermission")
                    notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        } else {
            // Android 13 (API 33) 미만 버전에서는 알림 권한이 필요 없음
            "Android 13 미만, 알림 권한 필요 없음".d("NotificationPermission")
            onPermissionResult(true) // 권한이 필요 없으므로 성공으로 처리
        }
    }

    if (showRationaleDialog.value) {
        AlertDialog(
            onDismissRequest = {
                showRationaleDialog.value = false
                onPermissionResult(false) // 사용자가 대화상자를 닫으면 거부로 간주
            },
            title = { Text("알림 권한 필요") },
            text = { Text("앱의 중요한 알림을 받기 위해서는 알림 권한이 필요합니다. 권한을 허용하시겠습니까?") },
            confirmButton = {
                TextButton(onClick = {
                    showRationaleDialog.value = false
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    }
                }) {
                    Text("다시 요청")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showRationaleDialog.value = false
                    onPermissionResult(false) // 사용자가 취소하면 거부로 간주
                }) {
                    Text("취소")
                }
            }
        )
    }

    if (showPermissionDeniedDialog.value) {
        AlertDialog(
            onDismissRequest = {
                showPermissionDeniedDialog.value = false
                onPermissionResult(false) // 설정으로 이동하지 않으면 거부로 간주
            },
            title = { Text("알림 권한 거부됨") },
            text = { Text("알림 권한이 거부되었습니다. 앱의 중요한 알림을 받으려면 앱 설정에서 직접 권한을 허용해야 합니다.") },
            confirmButton = {
                TextButton(onClick = {
                    showPermissionDeniedDialog.value = false
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", context.packageName, null)
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                    context.startActivity(intent)
                    // 설정 화면으로 이동 후 사용자의 행동을 알 수 없으므로,
                    // 여기서는 일단 onPermissionResult를 호출하지 않거나,
                    // 혹은 앱 재시작 시 권한 상태를 다시 확인하도록 유도할 수 있습니다.
                    // 이 예제에서는 일단 호출하지 않습니다. 필요에 따라 onPermissionResult(false)를 호출할 수 있습니다.
                }) {
                    Text("설정으로 이동")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showPermissionDeniedDialog.value = false
                    onPermissionResult(false)
                }) {
                    Text("취소")
                }
            }
        )
    }
}

fun requestPermissionNotification() {
    // 이 함수는 Composable 함수 내부에서 호출될 수 없으므로,
    // 알림 권한 요청은 @Composable fun RequestNotificationPermissionAlert() 를 직접 사용하거나
    // ViewModel 또는 다른 Non-Composable 로직에서 ActivityResultLauncher를 직접 관리해야 합니다.
    // 여기서는 사용 예시를 보여주기 어렵습니다.
    // Composable 컨텍스트가 필요하기 때문입니다.
    //
    // 만약 Activity나 Fragment에서 직접 권한을 요청하고 싶다면, 다음과 유사한 코드를 사용할 수 있습니다.
    // (이 코드는 여기에 직접 넣을 수 없고, 해당 클래스 내부에 구현해야 함)
    /*
    // In your Activity or Fragment:
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                // Permission is granted. Continue the action or workflow in your
                // app.
            } else {
                // Explain to the user that the feature is unavailable because the
                // features requires a permission that the user has denied. At the
                // same time, respect the user's decision. Don't link to system
                // settings in an effort to convince the user to change their
                // decision.
            }
        }

    fun askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                // FCM SDK (and your app) can post notifications.
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                // TODO: Display an educational UI explaining to the user the features that will be enabled
                //       by granting the POST_NOTIFICATION permission. This UI should provide the user
                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
                //       If the user selects "No thanks," allow the user to continue without notifications.
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
    */
}