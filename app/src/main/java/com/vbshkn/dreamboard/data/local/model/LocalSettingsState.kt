package com.vbshkn.dreamboard.data.local.model

data class LocalSettingsState(
    val memoryCache: Boolean,
    val discCache: Boolean,
    val darkTheme: Boolean
) {
    companion object {
        fun getDefaultState() = LocalSettingsState(
            memoryCache = true,
            discCache = true,
            darkTheme = false
        )
    }
}
