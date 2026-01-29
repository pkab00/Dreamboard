package com.vbshkn.dreamboard.domain.repository

import com.vbshkn.dreamboard.data.local.model.UserSettings
import kotlinx.coroutines.flow.Flow

interface UserSettingsRepository {
    fun getSettings(): Flow<UserSettings>
    suspend fun updateSettings(transform: (UserSettings) -> UserSettings)
}