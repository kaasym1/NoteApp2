package com.example.noteapp2

import android.app.Application
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

    }

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