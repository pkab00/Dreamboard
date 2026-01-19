package com.vbshkn.dreamboard.data.remote.model

import com.google.gson.annotations.SerializedName

data class ImageResponse(
    @SerializedName("id")
    val id: Long,
    @SerializedName("title")
    val title: String?,
    @SerializedName("content_type")
    val contentType: String,
    @SerializedName("thumbnail_url")
    val thumbnailUrl: String?,
    @SerializedName("thumbnail_2x_url")
    val thumbnailUrl2x: String?,
    @SerializedName("preview_url")
    val previewUrl: String?
)