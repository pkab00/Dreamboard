package com.vbshkn.dreamboard.domain.datasource

import com.vbshkn.dreamboard.data.remote.model.ImageListResponse
import com.vbshkn.dreamboard.data.remote.model.ImageResponse

interface ImageDataSource {
    suspend fun getImage(id: Long): ImageResponse
    suspend fun getImages(
        contentType: String, aiGenerated: Boolean?,
        term: String, page: Int, perPage: Int
    ): ImageListResponse
}