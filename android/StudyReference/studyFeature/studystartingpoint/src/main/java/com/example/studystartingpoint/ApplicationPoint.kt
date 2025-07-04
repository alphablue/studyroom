package com.example.studystartingpoint

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import com.example.studystartingpoint.util.d

val LOCATION_NOTI_CHANNEL = "위치정보알림"

class ApplicationPoint: Application() {

    fun makeNotification() {
        val channel = NotificationChannel(LOCATION_NOTI_CHANNEL, "위치알림", NotificationManager.IMPORTANCE_HIGH)
        channel.description = "위치정보 조회를 수행 하고 있습니다."
        (getSystemService(NOTIFICATION_SERVICE) as NotificationManager)
            .createNotificationChannel(channel)

        "알림 채널 생성 완료".d("locationService")
    }

    override fun onCreate() {
        super.onCreate()

        makeNotification()
    }
}