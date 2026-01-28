package com.vbshkn.dreamboard.data.remote.mapper

import com.vbshkn.dreamboard.data.remote.mapper.ImageMapper.toDomain
import com.vbshkn.dreamboard.data.remote.model.ImageListResponse
import com.vbshkn.dreamboard.domain.model.ImageListEntity

object ImageListMapper {
    fun ImageListResponse.toDomain() = ImageListEntity(
        page = page,
        lastPage = lastPage,
        perPage = perPage,
        totalImages = totalResources,
        imageEntities = resources.map { it.toDomain() }
    )
}