package com.vbshkn.dreamboard.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vbshkn.dreamboard.data.local.model.UserSettings
import com.vbshkn.dreamboard.domain.repository.UserSettingsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val settingsRepository: UserSettingsRepository
) : ViewModel() {
    val state = settingsRepository.getSettings()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UserSettings.getDefault()
        )

    fun onSwitchMemoryCaching(newValue: Boolean) {
        viewModelScope.launch {
            settingsRepository.updateMemoryCaching(newValue)
        }
    }

    fun onSwitchDickCaching(newValue: Boolean) {
        viewModelScope.launch {
            settingsRepository.updateDiscCaching(newValue)
        }
    }

    fun onSwitchDarkTheme(newValue: Boolean) {
        viewModelScope.launch {
            settingsRepository.updateDarkTheme(newValue)
        }
    }

    fun onSwitchDisableAi(newValue: Boolean) {
        viewModelScope.launch {
            settingsRepository.updateDisableAi(newValue)
        }
    }
}