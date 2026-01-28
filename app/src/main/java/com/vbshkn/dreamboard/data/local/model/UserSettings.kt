package com.vbshkn.dreamboard.data.local.model

data class UserSettings(
    val memoryCache: Boolean,
    val discCache: Boolean,
    val darkTheme: Boolean,
    val disableAi: Boolean
) {
    companion object {
        fun getDefault() = UserSettings(
            memoryCache = false,
            discCache = false,
            darkTheme = false,
            disableAi = false
        )
    }
}
