package com.vbshkn.dreamboard

import android.app.Application
import coil3.ImageLoader
import coil3.SingletonImageLoader
import org.koin.android.ext.android.inject

class App: Application() {
    private val imageLoader by inject<ImageLoader>()

    override fun onCreate() {
        super.onCreate()
        KoinManager(context = this).startFull()
        SingletonImageLoader.setSafe { imageLoader }
    }
}