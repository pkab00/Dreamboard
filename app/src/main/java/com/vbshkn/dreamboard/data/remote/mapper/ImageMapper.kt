package com.vbshkn.dreamboard.data.remote.mapper

import com.vbshkn.dreamboard.data.remote.model.ImageResponse
import com.vbshkn.dreamboard.domain.model.ImageEntity
import com.vbshkn.dreamboard.util.ImageFormat

object ImageMapper {
    fun ImageResponse.toDomain() = ImageEntity(
        id = id,
        title = title,
        format = ImageFormat.fromString(contentType),
        path = previewUrl
    )
}