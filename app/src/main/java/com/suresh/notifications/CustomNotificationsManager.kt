package com.suresh.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat

object CustomNotificationsManager {

    // Create multiple notification channel here using channel list
    fun Context.createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            getChannels().forEach {
                val channel =
                    NotificationChannel(
                        it.value.channelId,
                        it.value.channelName,
                        it.value.importance
                    )
                channel.description = it.value.channelDescription

                if (it.value.channelId == "ch9") {
                    val audioAttributes = AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_NOTIFICATION_RINGTONE)
                    channel.setSound(
                        getSoundUrl(),
                        audioAttributes.build()
                    )
                }


                val notificationManager: NotificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)
            }
        }
    }

    private fun Context.getSoundUrl(): Uri? {
        return Uri.parse(("android.resource://" + this.packageName) + "/" + R.raw.notification_sound)
    }

    // Pass channel class object with channel id, name and other notification details
    fun Context.showNotification(channel: ChannelDetails) {
        val intent = Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )


        val builder = NotificationCompat.Builder(this, channel.channelId)
            .setSmallIcon(R.drawable.ic_notification)

        if (channel.notificationType != "Custom Notification Layout") {
            builder
                .setContentTitle(channel.channelName)
                .setContentText(channel.channelDescription)
                .setPriority(channel.importance)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
        }

        when (channel.notificationType) {
            "Big Text" -> {
                builder.setStyle(
                    NotificationCompat.BigTextStyle()
                        .bigText("This is a longer text that will be displayed when the notification is expanded. This is a longer message that will be displayed in a big text style, allowing for multiple lines of text. You can include more detailed information here.")
                )
            }

            "inbox Style" -> {
                builder.setStyle(
                    NotificationCompat.InboxStyle()
                        .addLine("Message 1")
                        .addLine("Message 2")
                        .addLine("Message 3")
                )
            }

            "big Picture" -> {
                val bitmap =
                    BitmapFactory.decodeResource(resources, R.drawable.ic_notification_three)
                builder.setStyle(
                    NotificationCompat.BigPictureStyle()
                        .bigPicture(bitmap)
                )
            }

            "Progress Notification" -> {

                builder
                    .setProgress(100, 32, false)
            }

            "Silent Notification" -> {
                builder.setPriority(NotificationCompat.PRIORITY_LOW)
            }

            "Custom Notification Layout" -> {
                val customView = RemoteViews(this.packageName, R.layout.custom_notification_layout)
                customView.setTextViewText(R.id.notification_title, "Custom Notification")
                customView.setTextViewText(
                    R.id.notification_text,
                    "This is custom notification i have created"
                )
                customView.setTextViewText(R.id.notification_time, "10:00 AM")
                builder.setCustomContentView(customView)
                builder.setCustomBigContentView(customView)
                builder.setStyle(NotificationCompat.DecoratedCustomViewStyle())
            }

            "Different Sound" -> {

                builder.setSound(getSoundUrl())
                builder.setPriority(NotificationCompat.PRIORITY_HIGH)
            }

            "Big Icon" -> {
                val bitmap =
                    BitmapFactory.decodeResource(resources, R.drawable.ic_notification_three)

                builder.setLargeIcon(bitmap)

            }
        }

        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.notify(channel.id, builder.build())

    }

    // Create custom channel list with types
    fun getChannels(): Map<String, ChannelDetails> {
        return mutableListOf(
            ChannelDetails(
                id = 1,
                channelId = "ch1",
                channelName = "Normal No Popup",
                channelDescription = "Hello this notification without popup",
                importance = NotificationManager.IMPORTANCE_LOW
            ),
            ChannelDetails(
                id = 2,
                channelId = "ch2",
                channelName = "Heads-Up Notification",
                channelDescription = "This notification will appear at the top.",
                importance = NotificationManager.IMPORTANCE_HIGH
            ),
            ChannelDetails(
                id = 3,
                channelId = "ch3",
                channelName = "BigText",
                channelDescription = "Tap to see more.",
                importance = NotificationManager.IMPORTANCE_LOW,
                notificationType = "Big Text"
            ),
            ChannelDetails(
                id = 4,
                channelId = "ch4",
                channelName = "inbox Style",
                channelDescription = "You have new messages.",
                importance = NotificationManager.IMPORTANCE_LOW,
                notificationType = "inbox Style"
            ),
            ChannelDetails(
                id = 5,
                channelId = "ch5",
                channelName = "big Picture",
                channelDescription = "Big Picture Notification",
                importance = NotificationManager.IMPORTANCE_LOW,
                notificationType = "big Picture"
            ),
            ChannelDetails(
                id = 6,
                channelId = "ch6",
                channelName = "Progress Notification",
                channelDescription = "Downloading...",
                importance = NotificationManager.IMPORTANCE_LOW,
                notificationType = "Progress Notification"
            ),
            ChannelDetails(
                id = 7,
                channelId = "ch7",
                channelName = "Silent Notification",
                channelDescription = "This notification is silent.",
                importance = NotificationManager.IMPORTANCE_LOW,
                notificationType = "Silent Notification"
            ),
            ChannelDetails(
                id = 8,
                channelId = "ch8",
                channelName = "Custom Notification Layout",
                channelDescription = "This notification is silent.",
                importance = NotificationManager.IMPORTANCE_LOW,
                notificationType = "Custom Notification Layout"
            ),
            ChannelDetails(
                id = 9,
                channelId = "ch9",
                channelName = "Different Sound",
                channelDescription = "This is different sound notification.",
                importance = NotificationManager.IMPORTANCE_HIGH,
                notificationType = "Different Sound"
            ),
            ChannelDetails(
                id = 9,
                channelId = "ch10",
                channelName = "Big Icon",
                channelDescription = "This is big icon notification",
                importance = NotificationManager.IMPORTANCE_LOW,
                notificationType = "Big Icon"
            )


        ).associateBy { it.channelId }
    }

    // Channel data class
    data class ChannelDetails(
        var id: Int = 0,
        var channelId: String = "",
        var channelName: String = "",
        var channelDescription: String = "",
        var importance: Int = NotificationManager.IMPORTANCE_DEFAULT,
        var notificationType: String = "Default"
    )
}