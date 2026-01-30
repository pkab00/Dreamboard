package com.vbshkn.dreamboard.domain.usecase

import com.vbshkn.dreamboard.data.local.model.UserSettings
import com.vbshkn.dreamboard.domain.repository.UserSettingsRepository
import javax.inject.Inject

class UpdateUserSettingsUseCase @Inject constructor(
    private val userSettingsRepository: UserSettingsRepository
) {
    suspend operator fun invoke(transform: (UserSettings) -> UserSettings) {
        userSettingsRepository.updateSettings(transform)
    }
}