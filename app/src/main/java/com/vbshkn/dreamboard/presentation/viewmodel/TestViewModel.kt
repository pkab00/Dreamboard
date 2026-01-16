package com.vbshkn.dreamboard.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vbshkn.dreamboard.data.remote.ImageResource
import com.vbshkn.dreamboard.data.repository.ImageRepository
import com.vbshkn.dreamboard.util.NetworkResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TestViewModel(
    private val imageRepository: ImageRepository
) : ViewModel() {
    private var _imageResult = MutableStateFlow<NetworkResult<ImageResource>?>(null)
    val imageResult = _imageResult.asStateFlow()

    init {
        loadImage()
    }

    private fun loadImage(id: Long = 21848820) = viewModelScope.launch {
        _imageResult.value = imageRepository.getImage(id)
    }
}