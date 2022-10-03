package com.testnotification.basekotlinmvvm.utils

import android.app.Application

class App : Application() {
    companion object {
        @Volatile
        private var instance: App? = null

        @JvmStatic
        fun getInstance(): App = instance ?: synchronized(this) {
            instance ?: App().also {
                instance = it
            }
        }
    }
}