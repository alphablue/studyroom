package com.example.studystartingpoint.systemArch.gpsModule

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.studystartingpoint.ui.CommonDesignedComponent.Button.VerticalSpaceButton
import com.example.studystartingpoint.util.d

@Composable
fun GpsRunEntryPoint(
    paddingValues: PaddingValues
) {
    val context = LocalContext.current
    Column(
        Modifier
            .padding(paddingValues)
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        RequestGpsPermissionButton()

        VerticalSpaceButton(12.dp, {
            Button(
                modifier = Modifier
                    .wrapContentWidth()
                    .height(36.dp),
                shape = RoundedCornerShape(4.dp),
                onClick = {
                }
            ) {
                Text("GPS 실행 하기")
            }
        })
    }
}

@Composable
fun RequestGpsPermissionButton() {
    val context = LocalContext.current
    val activity = context as? Activity
    val showPermissionDeniedDialog = remember { mutableStateOf(false) }
    val showRationaleDialog = remember { mutableStateOf(false) }

    val prRequestList = rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
        "위치 권한 허용 상태 확인 ${it.keys} : ${it.values}".d("locationInfo")
        if (it.values.any { granted -> !granted }) {
            // 권한 중 하나라도 거부된 경우
            if (activity != null && it.keys.any { permission ->
                    !ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)
                }) {
                showPermissionDeniedDialog.value = true // "다시 묻지 않음" 상태
            } else {
                showRationaleDialog.value = true // 단순 거부 상태
            }
        }
    }

    val prRequest = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {

    }

    val locationPermissionList = mutableListOf<String>()

    if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
        "위치 정보에 대한 권한이 모두 있습니다.".d("locationInfo")
    } else if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        "대략적인 위치와 정확한 위치 정보에 대한 권한이 모두 없습니다.".d("locationInfo")
        locationPermissionList.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        locationPermissionList.add(Manifest.permission.ACCESS_FINE_LOCATION)
    } else {
        "위치 정보 권한 중 하나만 없거나 다른 상황입니다.".d("locationInfo")
        // 필요한 경우 여기에 추가적인 권한 요청 로직을 넣을 수 있습니다. (예: COARSE만 없거나, FINE만 없는 경우)
    }

    VerticalSpaceButton(12.dp) {
        Button(
            modifier = Modifier
                .wrapContentWidth()
                .height(36.dp),
            shape = RoundedCornerShape(4.dp),
            onClick = {
                prRequestList.launch(locationPermissionList.toTypedArray())
            }
        ) {
            Text("GPS 권한 요청 하기")
        }
    }

    if (showRationaleDialog.value) {
        AlertDialog(
            onDismissRequest = { showRationaleDialog.value = false },
            title = { Text("권한 필요") },
            text = { Text("GPS 기능을 사용하기 위해서는 위치 권한이 필요합니다. 다시 요청하시겠습니까?") },
            confirmButton = {
                TextButton(onClick = {
                    showRationaleDialog.value = false
                    prRequestList.launch(locationPermissionList.toTypedArray())
                }) {
                    Text("다시 요청")
                }
            },
            dismissButton = {
                TextButton(onClick = { showRationaleDialog.value = false }) {
                    Text("취소")
                }
            }
        )
    }

    if (showPermissionDeniedDialog.value) {
        AlertDialog(
            onDismissRequest = { showPermissionDeniedDialog.value = false },
            title = { Text("권한 거부됨") },
            text = { Text("위치 권한이 거부되었습니다. GPS 기능을 사용하려면 앱 설정에서 직접 권한을 허용해야 합니다.") },
            confirmButton = {
                TextButton(onClick = {
                    showPermissionDeniedDialog.value = false
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", context.packageName, null)
                    intent.data = uri
                    context.startActivity(intent)
                }) {
                    Text("설정으로 이동")
                }
            }
        )
    }
}

/**
 * GPS 권한을 요청하는 함수.
 * Android 버전에 따라 필요한 권한을 구분하여 요청합니다.
 *
 * @param activity 권한 요청을 수행할 Activity
 */
fun requestGpsPermission(activity: Activity) {
    val permissionsToRequest = mutableListOf<String>()
    val logTag = "locationInfo"

    // Android 12 (API 레벨 31) 이상인 경우
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        "Android 12 (API 레벨 31) 이상입니다.".d(logTag)
        // ACCESS_FINE_LOCATION (정확한 위치) 권한 확인
        if (ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            "ACCESS_FINE_LOCATION 및 ACCESS_COARSE_LOCATION 권한이 모두 부여되지 않았습니다. 권한을 요청합니다.".d(logTag)
            // 사용자는 정확한 위치 또는 대략적인 위치 중 하나를 선택할 수 있도록 두 권한을 모두 요청 목록에 추가합니다.
            // 시스템은 사용자에게 하나의 대화상자만 표시합니다.
            permissionsToRequest.add(Manifest.permission.ACCESS_FINE_LOCATION)
            permissionsToRequest.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        } else {
            "ACCESS_FINE_LOCATION 또는 ACCESS_COARSE_LOCATION 권한 중 하나 이상이 이미 부여되었습니다.".d(logTag)
        }
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        "Android 10 (API 레벨 29) 이상, Android 12 미만입니다.".d(logTag)
        // Android 10 (API 레벨 29) 이상, Android 12 미만인 경우
        // ACCESS_FINE_LOCATION (정확한 위치) 권한 확인
        if (ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            "ACCESS_FINE_LOCATION 권한이 부여되지 않았습니다. 권한을 요청합니다.".d(logTag)
            permissionsToRequest.add(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            "ACCESS_FINE_LOCATION 권한이 이미 부여되었습니다.".d(logTag)
        }
        // ACCESS_BACKGROUND_LOCATION (백그라운드 위치) 권한 확인 (Android 10 이상에서 필요)
        // 앱이 항상 위치 정보에 접근해야 하는 경우에만 요청합니다.
        // 대부분의 경우 포그라운드 위치 정보 접근 권한만으로 충분합니다.
        // 백그라운드 위치 권한은 사용자에게 더 큰 개인 정보 보호 우려를 야기할 수 있으므로 신중하게 요청해야 합니다.
        "ACCESS_BACKGROUND_LOCATION 권한을 확인합니다.".d(logTag)
        if (ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            "ACCESS_BACKGROUND_LOCATION 권한이 부여되지 않았습니다. 권한을 요청합니다.".d(logTag)
            // 사용자가 "항상 허용"을 선택할 수 있도록 백그라운드 위치 권한을 요청 목록에 추가합니다.
            // 이 권한은 별도의 대화상자를 통해 요청될 수 있습니다.
            permissionsToRequest.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
        } else {
            "ACCESS_BACKGROUND_LOCATION 권한이 이미 부여되었습니다.".d(logTag)
        }
    } else {
        "Android 10 (API 레벨 29) 미만입니다.".d(logTag)
        // Android 10 (API 레벨 29) 미만인 경우
        // ACCESS_FINE_LOCATION 또는 ACCESS_COARSE_LOCATION 권한만 확인하고 요청합니다.
        // 이 버전에서는 백그라운드 위치 권한이 별도로 존재하지 않습니다.
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            "ACCESS_FINE_LOCATION 및 ACCESS_COARSE_LOCATION 권한이 모두 부여되지 않았습니다. ACCESS_FINE_LOCATION 권한을 요청합니다.".d(logTag)
            permissionsToRequest.add(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            "ACCESS_FINE_LOCATION 또는 ACCESS_COARSE_LOCATION 권한 중 하나 이상이 이미 부여되었습니다.".d(logTag)
        }
    }

    if (permissionsToRequest.isNotEmpty()) {
        "요청할 권한 목록: $permissionsToRequest".d(logTag)
        ActivityCompat.requestPermissions(activity, permissionsToRequest.toTypedArray(), GPS_PERMISSION_REQUEST_CODE)
    } else {
        "요청할 권한이 없습니다. 모든 필요한 위치 권한이 이미 부여되었습니다.".d(logTag)
    }
}

const val GPS_PERMISSION_REQUEST_CODE = 1001 // GPS 권한 요청을 위한 고유 코드