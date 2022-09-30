package com.example.portfolio.ui.common.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import com.example.portfolio.R
import okhttp3.internal.notify

const val CHANNEL_ID = "Test Notification"
const val CHANNEL_NAME = "test Channel Name"
const val NOTIFICATION_ID = 10001

class NotificationBuilder(
    val context: Context
) {
    var builder = NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_baseline_emoji_food_beverage_24)
        .setContentTitle("테스트 알림")
        .setContentText("알림 테스트용")
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

    fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = CHANNEL_NAME
            val descriptionText = "테스트를 위한 앱 채널입니다."
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
            notificationManager.notify(NOTIFICATION_ID, builder.build())
        } else {
            with(NotificationManagerCompat.from(context)) {
                // notificationId is a unique int for each notification that you must define
                notify(NOTIFICATION_ID, builder.build())
            }
        }
    }
}