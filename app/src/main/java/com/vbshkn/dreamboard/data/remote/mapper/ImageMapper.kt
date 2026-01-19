package com.vbshkn.dreamboard.data.remote.mapper

import com.vbshkn.dreamboard.data.remote.model.ImageResponse
import com.vbshkn.dreamboard.domain.model.Image

object ImageMapper {
    fun ImageResponse.toDomain() = Image(
        id = id,
        title = title,
        format = contentType,
        path = previewUrl
    )
}