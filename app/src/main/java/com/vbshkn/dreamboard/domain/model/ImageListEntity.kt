package com.vbshkn.dreamboard.domain.model

data class ImageListEntity(
    val page: Int,
    val lastPage: Int,
    val perPage: Int,
    val totalImages: Long,
    val imageEntities: List<ImageEntity>
)
