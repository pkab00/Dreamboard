package com.vbshkn.dreamboard.data.local.datasource

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.vbshkn.dreamboard.data.local.model.UserSettings
import com.vbshkn.dreamboard.domain.datasource.UserSettingsSource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserSettingsDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) : UserSettingsSource {
    companion object {
        private val PREFS_NAME = preferencesDataStore("app_local_settings")
    }

    private object Keys {
        val MEMORY_CACHING = booleanPreferencesKey("memory_caching")
        val DISC_CACHING = booleanPreferencesKey("disc_caching")
        val DARK_THEME = booleanPreferencesKey("dark_theme")
        val DISABLE_AI = booleanPreferencesKey("disable_ai")
    }

    private val Context.localDataStore by PREFS_NAME
    private val defaults by lazy { UserSettings.getDefault() }

    override fun getSettings(): Flow<UserSettings> {
        return context.localDataStore.data.map { preferences ->
            UserSettings(
                memoryCache = preferences[Keys.MEMORY_CACHING] ?: defaults.memoryCache,
                discCache = preferences[Keys.DISC_CACHING] ?: defaults.discCache,
                darkTheme = preferences[Keys.DARK_THEME] ?: defaults.darkTheme,
                disableAi = preferences[Keys.DISABLE_AI] ?: defaults.disableAi
            )
        }
    }

    override suspend fun updateSettings(newSettings: UserSettings) {
        context.localDataStore.edit { preferences ->
            preferences[Keys.MEMORY_CACHING] = newSettings.memoryCache
            preferences[Keys.DISC_CACHING] = newSettings.discCache
            preferences[Keys.DARK_THEME] = newSettings.darkTheme
            preferences[Keys.DISABLE_AI] = newSettings.disableAi
        }
    }
}