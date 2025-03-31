package com.example.fastthirtyfivefinal.di

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class FastThirtyFiveApp: Application() {

    override fun onCreate() {
        super.onCreate()
    }
}