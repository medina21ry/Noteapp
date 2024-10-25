package com.example.noteapp

import AppDataBase
import android.app.Application
import androidx.room.Room

class App : Application() {

    companion object {
        var appDatabase: AppDataBase? = null
    }

    override fun onCreate() {
        super.onCreate()
        getInstance()
    }

    fun getInstance(): AppDataBase? {
        if (appDatabase == null) {
            appDatabase = applicationContext.let {
                Room.databaseBuilder(
                    it,
                    AppDataBase::class.java,
                    "note.database"
                ).fallbackToDestructiveMigration().allowMainThreadQueries().build()
            }
        }
        return appDatabase
    }
}