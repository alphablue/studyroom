package com.example.studystartingpoint.systemArch.alarmManager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.SystemClock
import android.provider.Settings
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.AlarmManagerCompat
import androidx.core.net.toUri
import com.example.studystartingpoint.ui.CommonDesignedComponent.Button.SpaceButton
import com.example.studystartingpoint.ui.CommonDesignedComponent.Toast.toastShort
import com.example.studystartingpoint.util.d

@Composable
fun AlarmManagerRunEntryPoint(paddingValues: PaddingValues) {
    val context = LocalContext.current
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    Column(
        Modifier
            .padding(paddingValues)
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SpaceButton(12.dp) {
            Button(
                modifier = Modifier
                    .wrapContentWidth()
                    .height(36.dp),
                shape = RoundedCornerShape(4.dp),
                onClick = {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        requestExactAlarmPermission(context)
                    }
                }
            ) {
                Text("정확한 알람 권한 요청하기")
            }
        }

        SpaceButton(12.dp) {
            Button(
                modifier = Modifier
                    .wrapContentWidth()
                    .height(36.dp),
                shape = RoundedCornerShape(4.dp),
                onClick = { startAlarmOneShot(context, alarmManager) }
            ) {
                Text("정확한 알람 등록하기")
            }
        }
    }
}

// 부정확한 알람 설정

// 정확한 알람 설정

// 정확한 알람을 위한 권한 설정
@RequiresApi(Build.VERSION_CODES.S)
fun requestExactAlarmPermission(
    context: Context
) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    if (!AlarmManagerCompat.canScheduleExactAlarms(alarmManager)) {
        val requestAlarmIntent = Intent(
            Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM,
            "package:${context.packageName}".toUri()
        )

        context.startActivity(requestAlarmIntent)
    } else {
        toastShort(context, "이미 정확한 알람 권한이 있습니다.")
    }
}

fun startAlarmOneShot(
    context: Context,
    alarmManager: AlarmManager
) {
    val alarmIntent = Intent(
        context,
        ExactListenBroadCast::class.java
    ).let {
        PendingIntent.getBroadcast(context, 0, it, 0)
    }

    alarmManager.set(
        AlarmManager.ELAPSED_REALTIME_WAKEUP,
        SystemClock.elapsedRealtime() + 60 * 1000,
        alarmIntent
    )

    "알람매니저 1분뒤 실행 요청 등록 완료".d("alarmTest")
}