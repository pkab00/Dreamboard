package com.vbshkn.dreamboard.presentation.state

import com.vbshkn.dreamboard.util.ImageFormat

data class SearchState (
    val searchingText: String = "",
    val currentPage: Int = 1,
    val selectedFormat: ImageFormat = ImageFormat.PHOTO,
    val disableAi: Boolean = false
)