package com.example.noteapp.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPreferenceHelper(context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        "shared",
        Context.MODE_PRIVATE
    )

    fun setOnBoardingComplete(isComplete: Boolean) {
        sharedPreferences.edit().putBoolean(SHOWED, isComplete).apply()
    }

    fun isOnBoardingComplete(): Boolean {
        return sharedPreferences.getBoolean(SHOWED, false)
    }

    fun setIsGridLayout(isGridLayout: Boolean) {
        sharedPreferences.edit().putBoolean(IS_GRID_LAYOUT, isGridLayout).apply()
    }

    fun getIsGridLayout(): Boolean {
        return sharedPreferences.getBoolean(IS_GRID_LAYOUT, false)
    }

    companion object{
        const val SHOWED = "SHOWED"
        const val IS_GRID_LAYOUT = "IS_GRID_LAYOUT"
    }
}