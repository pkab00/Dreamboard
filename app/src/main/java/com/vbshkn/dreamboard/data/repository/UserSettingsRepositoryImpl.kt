package com.vbshkn.dreamboard.data.repository

import com.vbshkn.dreamboard.domain.datasource.UserSettingsSource
import com.vbshkn.dreamboard.data.local.model.UserSettings
import com.vbshkn.dreamboard.domain.repository.UserSettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserSettingsRepositoryImpl @Inject constructor(
    private val localSettingsSource: UserSettingsSource
) : UserSettingsRepository {
    override fun getSettings(): Flow<UserSettings> {
        return localSettingsSource.getSettings()
    }

    override suspend fun updateSettings(transform: (UserSettings) -> UserSettings) {
        localSettingsSource.updateSettings(transform)
    }

}