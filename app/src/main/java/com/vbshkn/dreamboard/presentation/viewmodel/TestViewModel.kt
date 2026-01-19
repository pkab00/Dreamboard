package com.vbshkn.dreamboard.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vbshkn.dreamboard.data.repository.ImageRepository
import com.vbshkn.dreamboard.domain.model.Image
import com.vbshkn.dreamboard.presentation.state.TestScreenState
import com.vbshkn.dreamboard.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TestViewModel(
    private val imageRepository: ImageRepository
) : ViewModel() {
    private var _imageResults = MutableStateFlow(TestScreenState())
    val imageResults = _imageResults.asStateFlow()

    init {
        loadImages()
        loadImage()
    }

    private fun loadImage(id: Long = 21848820) = imageRepository.getImage(id).onEach { resource ->
        when (resource) {
            is Resource.Success -> _imageResults.update { screenState ->
                screenState.copy(
                    images = screenState.images + resource.body!!,
                    message = null,
                    isLoading = false
                )
            }
            is Resource.Error -> _imageResults.update { screenState ->
                screenState.copy(
                    message = resource.message,
                    isLoading = false
                )
            }
            is Resource.Loading -> _imageResults.update { screenState ->
                screenState.copy(
                    isLoading = true
                )
            }
        }
    }.launchIn(viewModelScope)

    private fun loadImages(
        term: String = "sun",
        page: Int = 1,
        perPage: Int = 100
    ) = imageRepository.getImages(term, page, perPage).onEach { resource ->
        when (resource) {
            is Resource.Success -> _imageResults.update { screenState ->
                screenState.copy(
                    images = screenState.images + (resource.body?.images ?: emptyList()),
                    message = null,
                    isLoading = false
                )
            }
            is Resource.Error -> _imageResults.update { screenState ->
                screenState.copy(
                    message = resource.message,
                    isLoading = false
                )
            }
            is Resource.Loading -> _imageResults.update { screenState ->
                screenState.copy(
                    isLoading = true
                )
            }
        }
    }.launchIn(viewModelScope)
}