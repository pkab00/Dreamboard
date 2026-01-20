package com.vbshkn.dreamboard.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vbshkn.dreamboard.data.repository.ImageRepository
import com.vbshkn.dreamboard.presentation.state.SearchScreenState
import com.vbshkn.dreamboard.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

class SearchViewModel(
    private val imageRepository: ImageRepository
) : ViewModel() {
    private var _imageResults = MutableStateFlow(SearchScreenState())
    val imageResults = _imageResults.asStateFlow()
    private var _currentPage = MutableStateFlow(1)

    init {
        loadImages()
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
        page: Int = _currentPage.value,
        perPage: Int = 50
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

    fun onLoadMore() {
        loadImages(page = ++_currentPage.value)
    }
}