package com.vbshkn.dreamboard.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vbshkn.dreamboard.domain.repository.UserSettingsRepository
import com.vbshkn.dreamboard.domain.usecase.GetUserSettingsUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class MainViewModel(
    private val getUserSettingsUseCase: GetUserSettingsUseCase
) : ViewModel() {
    val isDarkThemeEnabled = getUserSettingsUseCase()
        .map { it.darkTheme }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = null
        )
}