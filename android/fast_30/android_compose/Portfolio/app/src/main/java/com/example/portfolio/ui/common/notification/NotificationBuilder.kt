package com.example.portfolio.ui.common.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.TaskStackBuilder
import androidx.core.net.toUri
import com.example.portfolio.R
import com.example.portfolio.ui.navigation.Sections
import com.example.portfolio.ui.screen.profile.orderRoute

const val DELIVERY_CHANNEL_ID = "delivery_notify_channel"
const val DELIVERY_NOTI_CHANNEL_NAME = "배달알림"
const val NOTIFICATION_ID = 10001

class NotificationBuilder(
    private val context: Context,
    val userId: String = ""
) {

    fun createDeliveryNotificationChannel(
        doNotify: Boolean,
        notifyTitle: String = "",
        notifyContent: String = ""
    ) {
        val notNestedIntent = TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(
                Intent(
                    Intent.ACTION_VIEW,
                    "portfolio://profile_order_history?id=$userId".toUri()
                )
            )
            getPendingIntent(1234, PendingIntent.FLAG_IMMUTABLE)
        }

        val builder = NotificationCompat.Builder(context, DELIVERY_CHANNEL_ID)
            .apply {
                setSmallIcon(R.drawable.ic_baseline_emoji_food_beverage_24)
                priority = NotificationCompat.PRIORITY_DEFAULT

                if(doNotify) {
                    setContentTitle(notifyTitle)
                    setContentText(notifyContent)
                    setContentIntent(notNestedIntent)
                    setAutoCancel(true)
                }
            }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = DELIVERY_NOTI_CHANNEL_NAME
            val descriptionText = "배달 알림을 위한 채널 입니다."
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(DELIVERY_CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

            if(doNotify)
                notificationManager.notify(NOTIFICATION_ID, builder.build())

        } else {
            with(NotificationManagerCompat.from(context)) {
                notify(NOTIFICATION_ID, builder.build())
            }
        }
    }
}