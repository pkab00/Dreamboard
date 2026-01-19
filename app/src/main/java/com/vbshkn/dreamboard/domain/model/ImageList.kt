package com.vbshkn.dreamboard.domain.model

data class ImageList(
    val page: Int,
    val lastPage: Int,
    val perPage: Int,
    val totalImages: Long,
    val images: List<Image>
)
