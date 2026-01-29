package com.vbshkn.dreamboard.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vbshkn.dreamboard.data.local.model.UserSettings
import com.vbshkn.dreamboard.domain.repository.ImageRepository
import com.vbshkn.dreamboard.domain.repository.UserSettingsRepository
import com.vbshkn.dreamboard.domain.usecase.GetImageListUseCase
import com.vbshkn.dreamboard.domain.usecase.GetUserSettingsUseCase
import com.vbshkn.dreamboard.presentation.state.SearchScreenUIState
import com.vbshkn.dreamboard.presentation.state.SearchState
import com.vbshkn.dreamboard.util.ImageFormat
import com.vbshkn.dreamboard.util.Resource
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

@OptIn(FlowPreview::class)
class SearchViewModel(
    private val getImageListUseCase: GetImageListUseCase,
    private val getUserSettingsUseCase: GetUserSettingsUseCase
) : ViewModel() {
    private var _state = MutableStateFlow(SearchScreenUIState())
    val state = _state.asStateFlow()

    private val _localSettings = getUserSettingsUseCase()
    private var _searchingText = MutableStateFlow("")
    private var _selectedFormat = MutableStateFlow(ImageFormat.PHOTO)
    private var _currentPage = MutableStateFlow(1)
    private var _disableAi = MutableStateFlow(false)

    val localSettings = _localSettings
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UserSettings.getDefault()
        )

    val searchState = combine(
        _searchingText,
        _currentPage,
        _selectedFormat,
        _disableAi,
    ) { text, page, format, disableAi ->
        SearchState(
            searchingText = text,
            currentPage = page,
            selectedFormat = format,
            disableAi = disableAi
        )
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = SearchState()
        )

    init {
        observeDisableAiGlobal()
        launchSearchFlow()
    }

    private fun observeDisableAiGlobal() = _localSettings
        .map { it.disableAi }
        .onEach { _disableAi.value = it }
        .launchIn(viewModelScope)

    private fun launchSearchFlow() = combine(
        _searchingText.debounce { 500L },
        _selectedFormat,
        _disableAi
    ) { text, format, disableAi ->
        resetCurrentPage()
        resetImageList()
        if (text.isNotEmpty()) loadImages(
            format = format,
            aiGenerated = if (disableAi) false else null,
            term = text
        )
    }
        .debounce { 500L }
        .launchIn(viewModelScope)

    private fun loadImages(
        format: ImageFormat,
        aiGenerated: Boolean?,
        term: String,
        page: Int = _currentPage.value,
        perPage: Int = 50
    ) = getImageListUseCase(format, aiGenerated, term, page, perPage).onEach { resource ->
        when (resource) {
            is Resource.Success -> _state.update { screenState ->
                screenState.copy(
                    imageEntities = screenState.imageEntities + (resource.body?.imageEntities ?: emptyList()),
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
                imageEntities = emptyList()
            )
        }
    }

    private fun resetCurrentPage() = _currentPage.update { 1 }

    fun onAiModeChanged(newValue: Boolean) = _disableAi.update { newValue }

    fun onLoadMore() {
        loadImages(
            format = _selectedFormat.value,
            aiGenerated = if (_disableAi.value) false else null,
            term = _searchingText.value,
            page = ++_currentPage.value
        )
    }

    fun onRetry() {
        loadImages(
            format = _selectedFormat.value,
            aiGenerated = if (_disableAi.value) false else null,
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