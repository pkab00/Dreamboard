package com.vbshkn.dreamboard.data.local.model

data class LocalSettingsState(
    val memoryCache: Boolean,
    val discCache: Boolean,
    val darkTheme: Boolean,
    val disableAi: Boolean
) {
    companion object {
        fun getDefaultState() = LocalSettingsState(
            memoryCache = false,
            discCache = false,
            darkTheme = false,
            disableAi = false
        )
    }
}
