package com.vbshkn.dreamboard

import android.app.Application

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        KoinManager(context = this)
            .startFull()
    }
}