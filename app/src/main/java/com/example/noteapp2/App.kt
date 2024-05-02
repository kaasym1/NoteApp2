package com.example.noteapp2

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.core.app.NotificationManagerCompat
import androidx.room.Room
import com.example.noteapp2.data.AppDataBase
import com.example.noteapp2.data.Pref

class App : Application() {
    companion object {
        var appDataBase: AppDataBase? = null
    }

    override fun onCreate() {
        super.onCreate()
        getInstance()
       /* requestNotificationPermission(this)
        isNotificationPermissionGranted(this)*/

    }

/*
    fun requestNotificationPermission(activity: Context) {
        val notificationManager = NotificationManagerCompat.from(activity)
        if (!notificationManager.areNotificationsEnabled()) {
            // Показываем диалог с запросом разрешения на отправку уведомлений
            val intent = Intent().apply {
                action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
                putExtra(Settings.EXTRA_APP_PACKAGE, activity.packageName)
            }
            activity.startActivity(intent)
        }
    }


    fun isNotificationPermissionGranted(context: Context): Boolean {
        val notificationManager = NotificationManagerCompat.from(context)
        return notificationManager.areNotificationsEnabled()
    }
*/


    fun getInstance(): AppDataBase? {
        if (appDataBase == null) {
            appDataBase = applicationContext?.let {
                Room.databaseBuilder(
                    it,
                    AppDataBase::class.java,
                    "note.db"
                ).fallbackToDestructiveMigration().allowMainThreadQueries().build()
            }
        }
        return appDataBase

    }
}