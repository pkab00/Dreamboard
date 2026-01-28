package com.vbshkn.dreamboard.data.repository

import com.vbshkn.dreamboard.domain.datasource.UserSettingsSource
import com.vbshkn.dreamboard.data.local.model.UserSettings
import com.vbshkn.dreamboard.domain.repository.UserSettingsRepository
import kotlinx.coroutines.flow.Flow

class UserSettingsRepositoryImpl(
    private val localSettingsSource: UserSettingsSource
) : UserSettingsRepository {
    override fun getSettings(): Flow<UserSettings> {
        return localSettingsSource.getSettings()
    }

    override suspend fun updateMemoryCaching(enabled: Boolean) {
        return localSettingsSource.updateMemoryCaching(enabled)
    }

    override suspend fun updateDiscCaching(enabled: Boolean) {
        return localSettingsSource.updateDiscCaching(enabled)
    }

    override suspend fun updateDarkTheme(enabled: Boolean) {
        return localSettingsSource.updateDarkTheme(enabled)
    }

    override suspend fun updateDisableAi(enabled: Boolean) {
        return localSettingsSource.updateDisableAi(enabled)
    }
}