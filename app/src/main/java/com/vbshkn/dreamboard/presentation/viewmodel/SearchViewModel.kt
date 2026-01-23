package com.vbshkn.dreamboard.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vbshkn.dreamboard.data.repository.ImageRepository
import com.vbshkn.dreamboard.presentation.state.SearchScreenState
import com.vbshkn.dreamboard.util.ImageFormat
import com.vbshkn.dreamboard.util.Resource
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

@OptIn(FlowPreview::class)
class SearchViewModel(
    private val imageRepository: ImageRepository
) : ViewModel() {
    private var _state = MutableStateFlow(SearchScreenState())
    val state = _state.asStateFlow()

    private var _currentPage = MutableStateFlow(1)
    private var _searchingText = MutableStateFlow("")
    val searchingText = _searchingText.asStateFlow()

    private var _selectedFormat = MutableStateFlow<ImageFormat>(ImageFormat.PHOTO)
    val selectedFormat = _selectedFormat.asStateFlow()

    init { launchSearchFlow() }

    private fun launchSearchFlow() = _searchingText
        .debounce { 500L }
        .combine(_selectedFormat) { text, format ->
            resetCurrentPage()
            resetImageList()
            if (text.isNotEmpty()) loadImages(
                format = format,
                term = text
            )
        }
        .launchIn(viewModelScope)

    private fun loadImages(
        format: ImageFormat,
        term: String,
        page: Int = _currentPage.value,
        perPage: Int = 50
    ) = imageRepository.getImages(format, term, page, perPage).onEach { resource ->
        when (resource) {
            is Resource.Success -> _state.update { screenState ->
                screenState.copy(
                    images = screenState.images + (resource.body?.images ?: emptyList()),
                    message = null,
                    isLoading = false
                )
            }

            is Resource.Error -> _state.update { screenState ->
                screenState.copy(
                    message = resource.message,
                    isLoading = false
                )
            }

            is Resource.Loading -> _state.update { screenState ->
                screenState.copy(
                    isLoading = true
                )
            }
        }
    }.launchIn(viewModelScope)

    private fun resetImageList() {
        _state.update { state ->
            state.copy(
                images = emptyList()
            )
        }
    }

    private fun resetCurrentPage() = _currentPage.update { 1 }

    fun onLoadMore() {
        loadImages(
            format = _selectedFormat.value,
            term = _searchingText.value,
            page = ++_currentPage.value
        )
    }

    fun onRetry() {
        loadImages(
            format = _selectedFormat.value,
            term = _searchingText.value,
            page = _currentPage.value
        )
    }

    fun onSearchTextChange(searchText: String) {
        _searchingText.value = searchText
    }

    fun onFormatSelected(format: ImageFormat) {
        _selectedFormat.value = format
    }
}