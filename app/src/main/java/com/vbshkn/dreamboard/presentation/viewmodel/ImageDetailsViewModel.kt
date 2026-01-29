package com.vbshkn.dreamboard.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import coil3.request.CachePolicy
import com.vbshkn.dreamboard.domain.repository.ImageRepository
import com.vbshkn.dreamboard.domain.repository.UserSettingsRepository
import com.vbshkn.dreamboard.data.service.ImageDownloadService
import com.vbshkn.dreamboard.data.service.ShareDataService
import com.vbshkn.dreamboard.domain.model.ImageEntity
import com.vbshkn.dreamboard.domain.service.ImageDownloader
import com.vbshkn.dreamboard.domain.usecase.DownloadImageUseCase
import com.vbshkn.dreamboard.domain.usecase.GetImageUseCase
import com.vbshkn.dreamboard.domain.usecase.GetUserSettingsUseCase
import com.vbshkn.dreamboard.domain.usecase.ShareTextDataUseCase
import com.vbshkn.dreamboard.presentation.navigation.NavRoute
import com.vbshkn.dreamboard.presentation.state.ImageDetailsScreenState
import com.vbshkn.dreamboard.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ImageDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val getImageUseCase: GetImageUseCase,
    private val downloadImageUseCase: DownloadImageUseCase,
    private val shareTextDataUseCase: ShareTextDataUseCase,
    private val getUserSettingsUseCase: GetUserSettingsUseCase
) : ViewModel() {
    private val args = savedStateHandle.toRoute<NavRoute.ImageDetails>()
    val id = args.id
    private var _state = MutableStateFlow(ImageDetailsScreenState())
    val state = _state.asStateFlow()

    val memoryCachePolicy = getUserSettingsUseCase()
        .map { settingsState ->
            if(settingsState.memoryCache) CachePolicy.ENABLED
            else CachePolicy.DISABLED
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = CachePolicy.DISABLED
        )
    val discCachePolicy = getUserSettingsUseCase()
        .map { settingsState ->
            if (settingsState.discCache) CachePolicy.ENABLED
            else CachePolicy.DISABLED
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = CachePolicy.DISABLED
        )

    init {
        loadImage()
    }

    fun loadImage() = getImageUseCase(id).onEach { resource ->
        when (resource) {
            is Resource.Success -> _state.update { state ->
                state.copy(
                    imageEntity = resource.body,
                    isLoading = false
                )
            }
            is Resource.Error -> _state.update { state ->
                state.copy(
                    message = resource.message,
                    isLoading = false
                )
            }
            is Resource.Loading -> _state.update { state ->
                state.copy(
                    isLoading = true
                )
            }
        }
    }.launchIn(viewModelScope)

    fun downloadImageToDevice(imageEntity: ImageEntity) = viewModelScope.launch {
        downloadImageUseCase(imageEntity)
    }

    fun shareImage(imageEntity: ImageEntity) {
        shareTextDataUseCase(imageEntity.path!!, "Share the link")
    }
}