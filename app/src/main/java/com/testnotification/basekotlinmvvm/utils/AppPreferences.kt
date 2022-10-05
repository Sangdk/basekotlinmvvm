package com.testnotification.basekotlinmvvm.utils

import android.content.Context
import android.content.SharedPreferences

object AppPreferences {
    var preferences: SharedPreferences
    private var editor: SharedPreferences.Editor

    private const val REFERENCES_NAME = "AppPreferences"

    private object PrefKey {
        const val SCREEN_WIDTH = "screenWidth"

        const val SCREEN_HEIGHT = "screenHeight"
    }

    init {
        preferences = App.getInstance().getSharedPreferences(REFERENCES_NAME, Context.MODE_PRIVATE)
        editor = preferences.edit()
    }

    var screenWidth
        get() = preferences.getInt(PrefKey.SCREEN_WIDTH, 0)
        set(value) = editor.putInt(PrefKey.SCREEN_WIDTH, value).apply()

    var screenHeight
        get() = preferences.getInt(PrefKey.SCREEN_HEIGHT, 0)
        set(value) = editor.putInt(PrefKey.SCREEN_HEIGHT, value).apply()
}