package com.vbshkn.dreamboard

import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class KoinManager(private val context: Context) {
    private val dataModule = module {
        // TODO
    }

    fun startFull() = startKoin {
        androidContext(context)
        modules(
            dataModule
        )
    }
}