package com.vbshkn.dreamboard.domain.datasource

import com.vbshkn.dreamboard.data.local.model.UserSettings
import kotlinx.coroutines.flow.Flow

interface UserSettingsSource {
    fun getSettings(): Flow<UserSettings>
    suspend fun updateSettings(transform: (UserSettings) -> UserSettings)
}