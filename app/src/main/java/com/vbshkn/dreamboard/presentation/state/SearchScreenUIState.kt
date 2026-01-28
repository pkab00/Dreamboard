package com.vbshkn.dreamboard.presentation.state

import com.vbshkn.dreamboard.domain.model.ImageEntity

data class SearchScreenUIState (
    val imageEntities: List<ImageEntity> = emptyList(),
    val message: String? = null,
    val isLoading: Boolean = false
)