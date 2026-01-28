package com.vbshkn.dreamboard.presentation.state

import com.vbshkn.dreamboard.domain.model.ImageEntity

data class ImageDetailsScreenState(
    val imageEntity: ImageEntity? = null,
    val message: String? = null,
    val isLoading: Boolean = false
)
