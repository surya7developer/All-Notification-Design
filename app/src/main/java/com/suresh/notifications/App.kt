package com.suresh.notifications

import android.app.Application
import com.suresh.notifications.CustomNotificationsManager.createNotificationChannel

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }
}