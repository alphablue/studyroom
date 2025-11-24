package com.example.studystartingpoint

import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LifecycleStartEffect
import com.example.studystartingpoint.systemArch.alarmManager.ExactListenBroadCast
import com.example.studystartingpoint.ui.customCalendar.CalendarView
import com.example.studystartingpoint.ui.theme.StudyReferenceTheme
import com.example.studystartingpoint.util.d


class ActivityStartingPoint : ComponentActivity() {
    val permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {

    }

    @OptIn(ExperimentalTextApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StateFulBroadCastController()

            StudyReferenceTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    /**
                     * 알람 매니저 테스트 용
                     * */
//                    AlarmManagerRunEntryPoint(paddingValues = innerPadding)

                    /**
                     * gps 테스트 용
                     * */
//                    GpsRunEntryPoint(paddingValues = innerPadding)

                    /**
                     * shimmer 구현 및 drawable 객체와 paint의 작동
                     * */
//                    ShimmerEntryPoint()

//                    getCalenderInfo(this@ActivityStartingPoint)
                    CalendarView(innerPadding)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}

@Composable
fun StateFulBroadCastController(
) {
    val exactAlarmReceiver = remember { ExactListenBroadCast() }
    val context = LocalContext.current

    LifecycleStartEffect(true) {
//        registrationBroadCastReceiver(context, exactAlarmReceiver)
        onStopOrDispose {
//            unRegistrationBroadCastReceiver(context, exactAlarmReceiver)
            "리시버 등록 해제".d("alarmTest")
        }
    }
}

/**
 * 동적 리시버의 등록 방법
 * 해당 함수는 동적으로 리시버를 등록하고 해제할 때 활용된다. 만약에 이런 조절 없이 항상 받아야하는 리시버라면
 * 정적 등록 방법인 manifest 에서 <receiver> 태그로 등록 하자.
 * */
fun registrationBroadCastReceiver(
    context: Context,
    receiver: BroadcastReceiver,
    listenToBroadcastsFromOtherApps: Boolean = false
) {
    val receiverFlag = if(listenToBroadcastsFromOtherApps) {
        ContextCompat.RECEIVER_EXPORTED
    } else {
        ContextCompat.RECEIVER_NOT_EXPORTED
    }
    val intentFilter = IntentFilter(ExactListenBroadCast.ACTION_EXACT_ALARM_RECEIVER_TRIGGER)

    ContextCompat.registerReceiver(context, receiver, intentFilter,receiverFlag)
}

fun unRegistrationBroadCastReceiver(
    context: Context,
    receiver: BroadcastReceiver
) {
    context.unregisterReceiver(receiver)
}