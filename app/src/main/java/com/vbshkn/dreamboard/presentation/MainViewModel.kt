package com.vbshkn.dreamboard.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vbshkn.dreamboard.data.repository.LocalSettingsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class MainViewModel(
    private val localSettingsRepository: LocalSettingsRepository
) : ViewModel() {
    val isDarkThemeEnabled = localSettingsRepository.getSettings()
        .map { it.darkTheme }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = null
        )
}