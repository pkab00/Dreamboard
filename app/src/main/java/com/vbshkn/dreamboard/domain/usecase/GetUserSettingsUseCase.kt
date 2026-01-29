package com.vbshkn.dreamboard.domain.usecase

import com.vbshkn.dreamboard.data.local.model.UserSettings
import com.vbshkn.dreamboard.domain.repository.UserSettingsRepository
import kotlinx.coroutines.flow.Flow

class GetUserSettingsUseCase(
    private val userSettingsRepository: UserSettingsRepository
) {
    operator fun invoke(): Flow<UserSettings> {
        return userSettingsRepository.getSettings()
    }
}