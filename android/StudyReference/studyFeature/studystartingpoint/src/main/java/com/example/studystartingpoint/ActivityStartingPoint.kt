package com.example.studystartingpoint

import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LifecycleStartEffect
import com.example.studystartingpoint.systemArch.alarmManager.AlarmManagerRunEntryPoint
import com.example.studystartingpoint.systemArch.alarmManager.ExactListenBroadCast
import com.example.studystartingpoint.ui.theme.StudyReferenceTheme
import com.example.studystartingpoint.util.d


class ActivityStartingPoint : ComponentActivity() {
    @OptIn(ExperimentalTextApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StateFulBroadCastController()

            StudyReferenceTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AlarmManagerRunEntryPoint(paddingValues = innerPadding)
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
        registrationBroadCastReceiver(context, exactAlarmReceiver)
        onStopOrDispose {
            unRegistrationBroadCastReceiver(context, exactAlarmReceiver)
            "리시버 등록 해제".d("alarmTest")
        }
    }
}

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