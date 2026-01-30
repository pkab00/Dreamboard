package com.vbshkn.dreamboard.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vbshkn.dreamboard.data.local.model.UserSettings
import com.vbshkn.dreamboard.domain.repository.UserSettingsRepository
import com.vbshkn.dreamboard.domain.usecase.GetUserSettingsUseCase
import com.vbshkn.dreamboard.domain.usecase.UpdateUserSettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getUserSettingsUseCase: GetUserSettingsUseCase,
    private val updateUserSettingsUseCase: UpdateUserSettingsUseCase
) : ViewModel() {
    val state = getUserSettingsUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UserSettings.getDefault()
        )

    fun onSwitchMemoryCaching(newValue: Boolean) {
        viewModelScope.launch {
            updateUserSettingsUseCase { it.copy(memoryCache = newValue) }
        }
    }

    fun onSwitchDickCaching(newValue: Boolean) {
        viewModelScope.launch {
            updateUserSettingsUseCase { it.copy(discCache = newValue) }
        }
    }

    fun onSwitchDarkTheme(newValue: Boolean) {
        viewModelScope.launch {
            updateUserSettingsUseCase { it.copy(darkTheme = newValue) }
        }
    }

    fun onSwitchDisableAi(newValue: Boolean) {
        viewModelScope.launch {
            updateUserSettingsUseCase { it.copy(disableAi = newValue) }
        }
    }
}