package com.vbshkn.dreamboard.domain.model

import com.vbshkn.dreamboard.util.ImageFormat

data class Image(
    val id: Long,
    val title: String?,
    val format: ImageFormat,
    val path: String?,
)
