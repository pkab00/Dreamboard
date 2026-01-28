package com.vbshkn.dreamboard.data.remote.api

import com.vbshkn.dreamboard.data.remote.model.ImageListResponse
import com.vbshkn.dreamboard.data.remote.model.ImageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface VecteezyApi {
    @GET("/v2/%account_id%/resources/{id}")
    suspend fun getImage(@Path("id") id: Long) : Response<ImageResponse>

    @GET("/v2/%account_id%/resources")
    suspend fun getImages(
        @Query("content_type") contentType: String,
        @Query("ai_generated") aiGenerated: Boolean?,
        @Query("term") term: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ) : Response<ImageListResponse>
}