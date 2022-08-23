package com.example.portfolio.ui.screen.util.permission

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Lifecycle
import com.example.portfolio.ui.common.HardwareName
import com.example.portfolio.ui.common.PermissionName
import com.example.portfolio.ui.screen.util.findActivity
import com.example.portfolio.ui.screen.util.observeAsState

@Composable
fun PermissionCheck(
    permissionName: PermissionName,
    hardwareName: HardwareName
) {
    val context = LocalContext.current
    var alertDialogState by remember {
        mutableStateOf(false)
    }

    val lifecycleState = LocalLifecycleOwner.current.lifecycle.observeAsState()

    val requestPermission =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->

            alertDialogState = isGranted
        }

    when(lifecycleState) {
        Lifecycle.Event.ON_RESUME -> {
            if (context.packageManager.hasSystemFeature(
                    hardwareName.name
                )
            ) {
                Log.d("permissionCheck", "하드웨어 체크 [가능]")

                if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    Log.d("permissionCheck", "셀프 퍼미션 체크 [승인]")
                    alertDialogState = true
                } else {
                    Log.d("permissionCheck", "셀프 퍼미션 체크 [거부]")

                    if (ActivityCompat.shouldShowRequestPermissionRationale(
                            context.findActivity(),
                            Manifest.permission.ACCESS_FINE_LOCATION
                        )
                    ) {
                        Log.d("permissionCheck", "권한창 무시 [승인]")
                    } else {
                        // 다시보지 않기를 클릭 하고 승인 하지 않은 경우
                        Log.d("permissionCheck", "권한창 무시 [거부]")
                        alertDialogState = false
                    }
                }

            } else {
                Log.d("permissionCheck", "하드웨어 체크 [불가능]")
            }
        }
        else -> {

        }
    }


    Surface(Modifier.fillMaxSize()) {
        if(!alertDialogState) {
            PermissionDialog(
                onDismissClickEvent = { },
                confirmButtonEvent = {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(
                        Uri.fromParts(
                            "package",
                            context.packageName,
                            null
                        )
                    )
                    context.startActivity(intent)
                },
                dismissButtonEvent = {
                    context.findActivity().finish()
                }
            )
        }

        Box(modifier = Modifier.fillMaxSize()) {
            Button(
                onClick = { requestPermission.launch(Manifest.permission.ACCESS_FINE_LOCATION) },
                modifier = Modifier.align(Alignment.Center)
            ) {
                Text(text = "권한 요청")
            }
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