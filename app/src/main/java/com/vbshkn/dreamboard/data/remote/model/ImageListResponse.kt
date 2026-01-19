package com.vbshkn.dreamboard.data.remote.model

import com.google.gson.annotations.SerializedName

data class ImageListResponse (
    @SerializedName("page")
    val page: Int,
    @SerializedName("last_page")
    val lastPage: Int,
    @SerializedName("per_page")
    val perPage: Int,
    @SerializedName("total_resources")
    val totalResources: Long,
    @SerializedName("resources")
    val resources: List<ImageResponse>
)