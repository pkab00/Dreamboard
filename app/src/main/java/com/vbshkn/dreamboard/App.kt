package com.vbshkn.dreamboard

import android.app.Application
import coil3.ImageLoader
import coil3.SingletonImageLoader
import com.vbshkn.dreamboard.di.koin.KoinManager
import dagger.hilt.android.HiltAndroidApp
import org.koin.android.ext.android.inject

@HiltAndroidApp
class App: Application() {
    private val imageLoader by inject<ImageLoader>()

    override fun onCreate() {
        super.onCreate()
        KoinManager(context = this).startFull()
        SingletonImageLoader.setSafe { imageLoader }
    }
}