package com.example.studystartingpoint.systemArch.alarmManager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.studystartingpoint.util.d

/**
 * rolemanager 라는 것을 활용하는 경우가 있는데 어떤 기능을 제공하는 거지?
 * */

// 브로드캐스트는 registerReceiver 가 어느 context에 등록 되었는지에 따라서 생명주기가 달라진다.
// activity라는 activity가 살아 있는 동안, application 이라면 application 이 살아 있는 동안
class ExactListenBroadCast: BroadcastReceiver() {

    companion object {
        const val ACTION_EXACT_ALARM_RECEIVER_TRIGGER = "com.example.studystartingpoint.START_EXACT_ALARM_TEST"
    }

    override fun onReceive(context: Context, intent: Intent?) {
        "우리가 요청한 액션이 맞는가? ${intent?.action == ACTION_EXACT_ALARM_RECEIVER_TRIGGER}".d("alarmTest")

        "정확한 알람 브로드캐스트 리시버 테스트, ${intent?.getStringExtra("exactAlarm")}".d("alarmTest")
    }
}