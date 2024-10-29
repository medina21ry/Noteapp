package com.example.noteapp

import AppDataBase
import android.app.Application
import androidx.room.Room

class App : Application() {

    companion object {
        private lateinit var appDatabase: AppDataBase

        fun getDatabase(): AppDataBase {
            return appDatabase
        }
    }

    override fun onCreate() {
        super.onCreate()
        appDatabase = Room.databaseBuilder(
            applicationContext,
            AppDataBase::class.java,
            "note.database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}
