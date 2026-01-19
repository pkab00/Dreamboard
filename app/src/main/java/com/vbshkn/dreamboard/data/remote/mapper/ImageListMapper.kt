package com.vbshkn.dreamboard.data.remote.mapper

import com.vbshkn.dreamboard.data.remote.mapper.ImageMapper.toDomain
import com.vbshkn.dreamboard.data.remote.model.ImageListResponse
import com.vbshkn.dreamboard.domain.model.ImageList

object ImageListMapper {
    fun ImageListResponse.toDomain() = ImageList(
        page = page,
        lastPage = lastPage,
        perPage = perPage,
        totalImages = totalResources,
        images = resources.map { it.toDomain() }
    )
}