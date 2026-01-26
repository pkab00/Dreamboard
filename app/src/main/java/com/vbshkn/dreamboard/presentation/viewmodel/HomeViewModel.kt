package com.vbshkn.dreamboard.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil3.request.CachePolicy
import com.vbshkn.dreamboard.data.repository.ImageRepository
import com.vbshkn.dreamboard.data.repository.LocalSettingsRepository
import com.vbshkn.dreamboard.domain.model.Image
import com.vbshkn.dreamboard.util.ImageFormat
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

class HomeViewModel(
    private val imageRepository: ImageRepository,
    private val localSettingsRepository: LocalSettingsRepository
) : ViewModel() {
    private val _imageMap = MutableStateFlow<Map<String, List<Image>>>(
        mapOf(
            "nature" to emptyList(),
            "people" to emptyList(),
            "sea" to emptyList(),
            "dream" to emptyList()
        )
    )
    val imageMap = _imageMap.asStateFlow()
    private val picturesPerRow = 30
    val memoryCachePolicy = localSettingsRepository.getSettings()
        .map { settingsState ->
            if(settingsState.memoryCache) CachePolicy.ENABLED
            else CachePolicy.DISABLED
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = CachePolicy.DISABLED
        )
    val discCachePolicy = localSettingsRepository.getSettings()
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
        imageMap.value.keys.forEach { key ->
            loadImageRow(key)
        }
    }

    fun loadImageRow(term: String) =
        imageRepository.getImages(ImageFormat.PHOTO, term, 1, picturesPerRow)
            .onEach { resource ->
                when (resource) {
                    is Resource.Success -> {
                        resource.body?.let { imageList ->
                            _imageMap.update { map ->
                                map.toMutableMap().apply {
                                    this[term] = imageList.images
                                }
                            }
                        }
                    }

                    else -> {}
                }
            }.launchIn(viewModelScope)
}