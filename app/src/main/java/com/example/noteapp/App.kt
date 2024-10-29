package com.example.noteapp

import AppDataBase
import android.app.Application
import androidx.room.Room
import com.example.noteapp.utils.SharedPreferenceHelper

class App : Application() {
    companion object {
        var appDatabase: AppDataBase? = null
    }

    override fun onCreate() {
        super.onCreate()
        val sharedPreferences = SharedPreferenceHelper(this)
        sharedPreferences.unit(this)
        getInstance()
    }

    fun getInstance(): AppDataBase? {
        if (appDatabase == null) {
            appDatabase = Room.databaseBuilder(
                applicationContext,
                AppDataBase::class.java,
                "note_database"
            ).fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
        }
        return appDatabase
    }
}