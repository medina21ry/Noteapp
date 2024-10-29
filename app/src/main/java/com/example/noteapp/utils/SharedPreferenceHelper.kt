package com.example.noteapp.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPreferenceHelper(context: Context) {

    private lateinit var sharedPreference: SharedPreferences

    init {
        unit(context)
    }

    fun unit(context: Context){
        sharedPreference = context.getSharedPreferences("shared", Context.MODE_PRIVATE)
    }

    var title: String?
        get() = sharedPreference.getString("title", "")
        set(value) = sharedPreference.edit().putString("title", value)!!.apply()

    var isOnBoardShown: Boolean
        get() = sharedPreference.getBoolean("board", false)
        set(value) = sharedPreference.edit().putBoolean("board", value).apply()
}