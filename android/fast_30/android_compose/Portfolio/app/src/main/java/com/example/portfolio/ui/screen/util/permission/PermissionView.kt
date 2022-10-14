package com.example.portfolio.ui.screen.util.permission

import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.example.portfolio.ui.common.HardwareName
import com.example.portfolio.ui.common.PermissionName
import com.example.portfolio.ui.screen.util.findActivity
import com.example.portfolio.ui.screen.util.lifeCycleDetectorState

@Composable
fun PermissionCheck(
    permissionName: PermissionName,
    hardwareName: HardwareName,
    grantedCheck: (Boolean) -> Unit,
    onDismissClickEvent: () -> Unit = {},
    confirmButtonEvent: () -> Unit,
    dismissButtonEvent: () -> Unit
) {
    val context = LocalContext.current
    var alertDialogState by remember {
        mutableStateOf(true)
    }
    var doNotShowPermission by remember {
        mutableStateOf(false)
    }

    val lifecycleState = LocalLifecycleOwner.current.lifecycleScope

    val test = LocalLifecycleOwner.current.lifecycle

    val requestPermission =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->

            doNotShowPermission = isGranted
        }

    lifecycleState.launchWhenResumed {
        val isHardWareOk =
            hardwareName.packageManager.filter {
                context.packageManager.hasSystemFeature(it)
            }.size == hardwareName.packageManager.size

        if (isHardWareOk
        ) {
            Log.d("permissionCheck", "하드웨어 체크 [가능]")

            // 권한 거부중에 상세 설명이 필요한 경우 그 갯수가 0일 때 모든 필수 권한을 허용한 상태이다.
            val isShowRequestPermissionAllOk =
                permissionName.permissionName.none {
                    val check = ActivityCompat.shouldShowRequestPermissionRationale(
                        context.findActivity(),
                        it
                    )

                    Log.d("permissionCheck", "permission name : $it , return check : $check")
                    check
                }

            if (isShowRequestPermissionAllOk) {
                Log.d("permissionCheck", "권한창 무시 [승인]")

                val isCheckSelfPermissionOk =
                    permissionName.permissionName.filter {
                        context.checkSelfPermission(it) == PackageManager.PERMISSION_GRANTED
                    }.size == permissionName.permissionName.size

                if (isCheckSelfPermissionOk) {
                    Log.d("permissionCheck", "셀프 퍼미션 체크 [승인]")
                    alertDialogState = true
                    grantedCheck(true)

                } else {
                    Log.d("permissionCheck", "셀프 퍼미션 체크 [거부]")

                    permissionName.permissionName.forEach {
                        requestPermission.launch(it)
                    }
                }
            } else {
                // 다시보지 않기를 클릭 하고 승인 하지 않은 경우
                if (!doNotShowPermission) {
                    Log.d("permissionCheck", "권한창 무시 [거부]")
                    alertDialogState = false
                }
                grantedCheck(false)
            }

        } else {
            Log.d("permissionCheck", "하드웨어 체크 [불가능]")
            grantedCheck(false)
        }
    }

    lifeCycleDetectorState(test.currentState)

    Surface(Modifier.fillMaxSize()) {
        if (!alertDialogState) {
            PermissionDialog(
                onDismissClickEvent = onDismissClickEvent,
                confirmButtonEvent = confirmButtonEvent,
                dismissButtonEvent = dismissButtonEvent
            )
        }
    }
}

@Composable
fun PermissionDialog(
    onDismissClickEvent: () -> Unit,
    confirmButtonEvent: () -> Unit,
    dismissButtonEvent: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissClickEvent,
        title = { Text(text = "권한이 필요합니다.") },
        text = { Text(text = "앱의 원활한 사용을 위해서는 권한을 허용 해 주세요.") },
        confirmButton = {
            Button(onClick = confirmButtonEvent) {
                Text(text = "권한 설정")
            }
        },
        dismissButton = {
            Button(onClick = dismissButtonEvent) {
                Text(text = "취소")
            }
        })
}