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
import com.example.studystartingpoint.ui.CommonDesignedComponent.Button.VerticalSpaceButton
import com.example.studystartingpoint.ui.CommonDesignedComponent.Toast.makeToastShort
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
        VerticalSpaceButton(12.dp, {
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
        })

        VerticalSpaceButton(12.dp, {
            Button(
                modifier = Modifier
                    .wrapContentWidth()
                    .height(36.dp),
                shape = RoundedCornerShape(4.dp),
                onClick = {
                    startAlarmOneShot(context, alarmManager)
                }
            ) {
                Text("정확한 알람 등록하기")
            }
        })
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
        makeToastShort(context, "이미 정확한 알람 권한이 있습니다.")
    }
}

fun startAlarmOneShot(
    context: Context,
    alarmManager: AlarmManager
) {
    /**
     * 참조: https://developer.android.com/reference/android/app/PendingIntent
     *
     * pendingIntent 를 만들 때 주의 할 점은 intent의 "추가된" 내용만 다른 intent 를 만들어 줬을 때,
     * 매번 다른 pendingIntent 를 생성하지 않는 다는 것이다. 같은 pendingIntent를 생성하게 되면서 여러개의 실행을 요청했더라도
     * 하나로 간주될 수 있다. 이것을 방지 하기 위해서는 requestCode 를 명확하게 정의해 줘야 한다.
     *
     * 만약 매번 하나의 pendingIntent를 생성해야 한다면 PendingIntent.FLAG_CANCEL_CURRENT 나 PendingIntent.FLAG_UPDATE_CURRENT 를 flag로 넘겨 주자
     * */

    val alarmIntent = Intent(
        context,
        ExactListenBroadCast::class.java
    ).let {
        it.action = ExactListenBroadCast.ACTION_EXACT_ALARM_RECEIVER_TRIGGER
        it.putExtra("exactAlarm", "액션의 등록 완료")
        // 12 이상인 경우 PendingIntent.FLAG_IMMUTABLE을 사용해야함 (보안 강화의 목적)
        PendingIntent.getBroadcast(context, 0, it, 0 or PendingIntent.FLAG_IMMUTABLE)
    }

    alarmManager.set(
        AlarmManager.ELAPSED_REALTIME_WAKEUP,
        SystemClock.elapsedRealtime() + 30 * 1000,
        alarmIntent
    )

    /**
     * 알람 및 리마인더 권한이 허용되어 있지 않다면 아래 함수 호출시 app crash가 발생 한다.
     * */
//    alarmManager.setExact(
//        AlarmManager.ELAPSED_REALTIME_WAKEUP,
//        SystemClock.elapsedRealtime() + 30 * 1000,
//        alarmIntent
//    )

    "알람매니저 1분뒤 실행 요청 등록 완료".d("alarmTest")
}