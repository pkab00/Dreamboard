package com.vbshkn.dreamboard.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vbshkn.dreamboard.data.local.model.LocalSettingsState
import com.vbshkn.dreamboard.data.repository.LocalSettingsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val settingsRepository: LocalSettingsRepository
) : ViewModel() {
    val state = settingsRepository.getSettings()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = LocalSettingsState.getDefaultState()
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
}