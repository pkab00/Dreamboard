package com.vbshkn.dreamboard.presentation.state

import com.vbshkn.dreamboard.domain.model.Image

data class SearchScreenState (
    val images: List<Image> = emptyList(),
    val message: String? = null,
    val isLoading: Boolean = false
)