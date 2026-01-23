package com.vbshkn.dreamboard.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.vbshkn.dreamboard.data.repository.ImageRepository
import com.vbshkn.dreamboard.data.service.ImageDownloadService
import com.vbshkn.dreamboard.data.service.ShareDataService
import com.vbshkn.dreamboard.domain.model.Image
import com.vbshkn.dreamboard.presentation.navigation.NavRoute
import com.vbshkn.dreamboard.presentation.state.ImageDetailsScreenState
import com.vbshkn.dreamboard.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ImageDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val imageRepository: ImageRepository,
    private val imageDownloadService: ImageDownloadService,
    private val shareDataService: ShareDataService
) : ViewModel() {
    private val args = savedStateHandle.toRoute<NavRoute.ImageDetails>()
    val id = args.id
    private var _state = MutableStateFlow(ImageDetailsScreenState())
    val state = _state.asStateFlow()

    init {
        loadImage()
    }

    fun loadImage() = imageRepository.getImage(id).onEach { resource ->
        when (resource) {
            is Resource.Success -> _state.update { state ->
                state.copy(
                    image = resource.body,
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

    fun downloadImageToDevice(image: Image) = viewModelScope.launch {
        imageDownloadService.downloadAsJpeg(image)
    }

    fun shareImage(image: Image) {
        shareDataService.shareString(image.path!!, "Share the link")
    }
}