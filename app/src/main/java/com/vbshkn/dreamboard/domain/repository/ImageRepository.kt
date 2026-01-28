package com.vbshkn.dreamboard.domain.repository

import com.vbshkn.dreamboard.domain.model.ImageEntity
import com.vbshkn.dreamboard.domain.model.ImageListEntity
import com.vbshkn.dreamboard.util.ImageFormat
import com.vbshkn.dreamboard.util.Resource
import kotlinx.coroutines.flow.Flow

interface ImageRepository {
    fun getImage(id: Long): Flow<Resource<ImageEntity>>
    fun getImages(
        imageFormat: ImageFormat, aiGenerated: Boolean?,
        term: String, page: Int, perPage: Int
    ): Flow<Resource<ImageListEntity>>
}