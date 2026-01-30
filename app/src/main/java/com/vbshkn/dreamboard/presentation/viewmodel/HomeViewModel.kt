package com.vbshkn.dreamboard.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil3.request.CachePolicy
import com.vbshkn.dreamboard.domain.repository.ImageRepository
import com.vbshkn.dreamboard.domain.repository.UserSettingsRepository
import com.vbshkn.dreamboard.domain.model.ImageEntity
import com.vbshkn.dreamboard.domain.usecase.GetImageListUseCase
import com.vbshkn.dreamboard.domain.usecase.GetUserSettingsUseCase
import com.vbshkn.dreamboard.util.ImageFormat
import com.vbshkn.dreamboard.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getImageListUseCase: GetImageListUseCase,
    private val getUserSettingsUseCase: GetUserSettingsUseCase
) : ViewModel() {
    private val _imageEntityMap = MutableStateFlow<Map<String, List<ImageEntity>>>(
        mapOf(
            "nature" to emptyList(),
            "people" to emptyList(),
            "sea" to emptyList(),
            "dream" to emptyList()
        )
    )
    val imageMap = _imageEntityMap.asStateFlow()
    private val picturesPerRow = 30
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
        imageMap.value.keys.forEach { key ->
            loadImageRow(key)
        }
    }

    fun loadImageRow(term: String) =
        getImageListUseCase(ImageFormat.PHOTO, null, term, 1, picturesPerRow)
            .onEach { resource ->
                when (resource) {
                    is Resource.Success -> {
                        resource.body?.let { imageList ->
                            _imageEntityMap.update { map ->
                                map.toMutableMap().apply {
                                    this[term] = imageList.imageEntities
                                }
                            }
                        }
                    }
                    else -> {}
                }
            }.launchIn(viewModelScope)
}