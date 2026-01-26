package com.vbshkn.dreamboard.data.repository

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.vbshkn.dreamboard.data.local.model.LocalSettingsState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class LocalSettingsRepository(private val context: Context) {
    companion object {
        private val PREFS_NAME = preferencesDataStore("app_local_settings")
    }

    private object Keys {
        val MEMORY_CACHING = booleanPreferencesKey("memory_caching")
        val DISC_CACHING = booleanPreferencesKey("disc_caching")
        val DARK_THEME = booleanPreferencesKey("dark_theme")
    }

    private val Context.localDataStore by PREFS_NAME
    private val defaults by lazy { LocalSettingsState.getDefaultState() }

    fun getSettings(): Flow<LocalSettingsState> =
        context.localDataStore.data.map { preferences ->
            LocalSettingsState(
                memoryCache = preferences[Keys.MEMORY_CACHING] ?: defaults.memoryCache,
                discCache = preferences[Keys.DISC_CACHING] ?: defaults.discCache,
                darkTheme = preferences[Keys.DARK_THEME] ?: defaults.darkTheme
            )
        }

    suspend fun updateMemoryCaching(enabled: Boolean) {
        context.localDataStore.edit { preferences ->
            preferences[Keys.MEMORY_CACHING] = enabled
        }
    }

    suspend fun updateDiscCaching(enabled: Boolean) {
        context.localDataStore.edit { preferences ->
            preferences[Keys.DISC_CACHING] = enabled
        }
    }

    suspend fun updateDarkTheme(enabled: Boolean) {
        context.localDataStore.edit { preferences ->
            preferences[Keys.DARK_THEME] = enabled
        }
    }

    suspend fun isMemoryCachingEnabled(): Boolean {
        return getSettings().first().memoryCache
    }

    suspend fun isDiscCachingEnabled(): Boolean {
        return getSettings().first().discCache
    }

    suspend fun isDarkThemeEnabled(): Boolean {
        return getSettings().first().darkTheme
    }
}